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

(defn merge-tips
  [tips]
  (vector (reduce #(str %1 "|" %2) tips)))

(defn tips->paths
  "Cascalog query that builds a taxon bipartition table and saves it to a text file."
  [tree-file tips-file sink-path]
  (let [tree-src (hfs-delimited (get-path tree-file) :delimiter ",")
        tips-src (hfs-delimited (get-path tips-file) :delimiter ",")
        sink (hfs-delimited sink-path :delimiter "\t" :sinkmode :replace)
        tuple-src (<-
                   [?tips ?node ?tips-count]
                   (tree-src ?path-tip ?path)
                   (tips-src ?path-tip)
                   (map-nodes ?path-tip ?path :> ?node ?tip)
                   (combine-nodes ?tip :> ?tips)
                   (count ?tips :> ?tips-count)
                   (:trap (stdout)))]
    (?<- sink
     [?tips-merged ?node-out ?tips-count]
     (tuple-src ?tips ?node ?tips-count)
     (> ?tips-count 1)
     (:sort ?node)
     (merge-tips ?tips :> ?tips-merged)
     (c/limit [1] ?node :> ?node-out))))

(defmain PruneTree [tree-file tips-file sink-path]
  (tips->paths tree-file tips-file sink-path))
