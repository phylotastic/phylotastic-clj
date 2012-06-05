(defproject phylotastic-clj "0.1.0-SNAPSHOT"
  :description "Phylotastic Clojure implementation."
  :repositories {"conjars" "http://conjars.org/repo/"}
  :source-path "src/clj"
  :resources-path "resources"
  :dev-resources-path "dev"
  :jvm-opts ["-XX:MaxPermSize=256M"
             "-XX:+UseConcMarkSweepGC"
             "-Xms1024M" "-Xmx1048M" "-server"]
  :plugins [[lein-midje "1.0.8"]
            [swank-clojure "1.4.0-SNAPSHOT"]]
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [cascalog "1.9.0-wip8"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.4.0"]])
