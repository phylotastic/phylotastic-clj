(ns phylotastic.playground
  "This namespace defines example Cascalog taps for experimenting with.")

(def node-tip-tap
  [["36" "Bdellomicrovirus"]
   ["36" "Chlamydiamicrovirus"]
   ["36" "Spiromicrovirus"]
   ["8" "Bdellomicrovirus"]
   ["8" "Chlamydiamicrovirus"]
   ["8" "Spiromicrovirus"]
   ["8134" "Bdellomicrovirus"]
   ["8134" "Chlamydiamicrovirus"]
   ["8134" "Spiromicrovirus"]
   ["8135" "Bdellomicrovirus"]
   ["8135" "Chlamydiamicrovirus"]
   ["8135" "Spiromicrovirus"]])


(def node-combined-tap
  [["36" ["Bdellomicrovirus" "Chlamydiamicrovirus" "Spiromicrovirus"]]
   ["8" ["Bdellomicrovirus" "Chlamydiamicrovirus" "Spiromicrovirus"]]
   ["8134" ["Bdellomicrovirus" "Chlamydiamicrovirus" "Spiromicrovirus"]]
   ["8135" ["Bdellomicrovirus" "Chlamydiamicrovirus" "Spiromicrovirus"]]])

(def node-combined-2-tap
  [[["36" ["Bdellomicrovirus" "Chlamydiamicrovirus" "Spiromicrovirus"]]]
   [["8" ["Bdellomicrovirus" "Chlamydiamicrovirus" "Spiromicrovirus"]]]
   [["8134" ["Bdellomicrovirus" "Chlamydiamicrovirus" "Spiromicrovirus"]]]
   [[ "8135" ["Bdellomicrovirus" "Chlamydiamicrovirus" "Spiromicrovirus"]]]])

(def test-tap [["a" 1] 
               ["b" 2]
               ["a" 3]])


(def src
  [[1 ["A"]]
   [2 ["C"]]
   [3 ["A" "C"]]
   [4 ["A" "C"]]
   [5 ["F" "G"]]
   [6 ["A" "C" "F" "G"]]])

;; [?id ?coll-count ?coll]
(def src 
  [[1 1 ["A"]]
   [2 1 ["C"]]
   [3 2 ["A" "C"]]
   [4 2 ["A" "C"]]
   [5 2 ["F" "G"]]
   [6 4 ["A" "C" "F" "G"]]])

;; 1) Filter out tuples where ?count is less than 1
;; 2) For tuples with the same ?coll, keep the tuple with the minimum ?id

(def result
  [[3 2 ["A" "C"]]
   [5 2 ["F" "G"]]
   [6 4 ["A" "C" "F" "G"]]])



