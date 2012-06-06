(ns phylotastic.core
  "This namespace defines Cascalog queries for exploring phylogenetic trees."
  (:use [cascalog.api]
        [clojure.string :only (join split)])
  (:require [clojure.java.io :as io]
            [cascalog.ops :as c]))

(defn get-path
  "Returns the full path of the supplied resource name."
  [name]
  (.getPath (io/resource name)))

(defn path->nodes
  "Return a vector of nodes from the supplied path delineated by '|' character."
  [path]
  (split path #"\|"))

(defmapcatop map-nodes 
  "Return sequence of tuples [node tip] for all nodes in supplied path."
  [tip path]
  (for [node (path->nodes path)]
    [(Integer/parseInt node) tip]))

(defbufferop combine-nodes
  "Return vector of tips."
  [tips]
  [(vector (vec (map first tips)))])

(defn tips->paths
  "Cascalog query that builds a taxon bipartition table "
  [tree-path tips-path]
  (let [tree-src (hfs-delimited tree-path :delimiter ",")
        tips-src (hfs-delimited tips-path :delimiter ",")
        tuple-src (<-
                   [?tips ?node ?tips-count]
                   (tree-src ?path-tip ?path)
                   (tips-src ?path-tip)
                   (map-nodes ?path-tip ?path :> ?node ?tip)
                   (combine-nodes ?tip :> ?tips)
                   (count ?tips :> ?tips-count)
                   (:trap (stdout)))]
    (??<-
     [?tips ?node-out ?tips-count]
     (tuple-src ?tips ?node ?tips-count)
     (> ?tips-count 1)
     (:sort ?node)
     (c/limit [1] ?node :> ?node-out))))
