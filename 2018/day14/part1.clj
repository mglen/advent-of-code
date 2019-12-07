
(defn recipes-until [start run-till]
  (loop [recipes start
         elf-indexes [0 1]
         recipes-count (count start)]
    (if (>= recipes-count (+ run-till 10))
      (take 10 (drop run-till recipes)) ; number can jump by 2
      (let [last-recipes (map (partial nth recipes) elf-indexes)
            tmp-score (apply + last-recipes)
            recipes-to-add (if (> tmp-score 9) [1 (mod tmp-score 10)] [tmp-score])
            new-recipes (apply conj recipes recipes-to-add)
            new-recipes-count (+ recipes-count (count recipes-to-add))
            new-indexes (map #(mod % new-recipes-count) (map (partial + 1) elf-indexes last-recipes))]
;        (println "last-recipes" last-recipes "tmp-score" tmp-score "to-add" recipes-to-add "new-indexes" new-indexes "new-count" new-recipes-count)
        (recur
               new-recipes
               new-indexes
               new-recipes-count)))))
      
(let [start [3 7]
      run-till 760221]
  (println (apply str (recipes-until start run-till))))

; 4113836210 too high
