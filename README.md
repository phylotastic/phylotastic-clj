# About

This project contains a Clojure/Cascalog implementation for pruning phylogenetic trees. It rides on [this algorithm](https://github.com/phylotastic/tolomatic) for pre-processing trees so that they can be parallelized and pruned using MapReduce. 

# Running

You can run this from the shell:

```shell
hadoop jar phylotastic-0.1.0-SNAPSHOT-standalone.jar phylotastic.core.PruneTree /path/to/tol.csv /path/to/names.csv /tmp/results
```

Or by firing up the REPL:

```clojure
(use `phylotastic.core)
(use `cascalog.api)
(tips->path "/path/to/tol.csv" "/path/to/names.csv" "/tmp/results")
```
