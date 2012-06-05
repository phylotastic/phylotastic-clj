(ns phylotastic-clj.core
  "This namespace defines Cascalog queries for exploring phylogenetic trees."
  (:use cascalog.api)
  (:require [clojure.java.io :as io]))

(defn get-path
  "Returns the full path of the supplied resource name."
  [name]
  (.getPath (io/resource name)))

(defn tips->paths
  "Cascalog query that extracts paths from the supplied tree-src and tips-path."
  [tree-path tips-path]
  (let [tree-src (hfs-delimited tree-path :delimiter ",")
        tips-src (hfs-delimited tips-path :delimiter ",")]
    (??<- 
     [?tip ?path]
     (tree-src ?tip ?path)
     (tips-src ?tip)
     (:trap (stdout)))))
