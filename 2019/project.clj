(defproject aoc2019 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.bhauman/rebel-readline "0.1.4"]]
  :main aoc2019.core
  :aliases {"rebl" ["trampoline" "run" "-m" "rebel-readline.main"]}
  :repl-options {:init-ns aoc2019.core})
