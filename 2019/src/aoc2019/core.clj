(ns aoc2019.core
  (:require [clojure.java.io :as io]))

; Common functions for AOC problems
(defn to-int [v]
  (Integer/parseInt v))

(defn get-input [resource-name]
  (slurp (io/resource resource-name)))

(defn -main [& args]
  (println "Hello, Advent of Code!"))
