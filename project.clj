(defproject om-cookbook "0.1.0-SNAPSHOT"
  :description "A Book of Receipts for Om 1.0.0 (next)"
  :dependencies [[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/clojurescript "1.7.170" :scope "provided"]
                 [org.clojure/core.async "0.2.374"]
                 [devcards "0.2.1-2" :exclusions [org.omcljs/om cljsjs/react-dom org.clojure/tools.reader cljsjs/react]]
                 [org.omcljs/om "1.0.0-alpha26"]
                 [figwheel-sidecar "0.5.0-2" :exclusions [clj-time joda-time org.clojure/tools.reader] :scope "test"]
                 [cljsjs/codemirror "5.8.0-0"]]

  :source-paths ["src/cookbook"]

  :plugins [[lein-cljsbuild "1.1.1"]]

  :clean-targets ^{:protect false} ["resources/public/js" "resources/public/cookbook" "target"]

  :figwheel {:build-ids   ["cookbook"]
             :server-port 3451}

  :cljsbuild {
              :builds
              [
               {:id           "cookbook"
                :figwheel     {:devcards true}
                :source-paths ["src/cookbook"]
                :compiler     {
                               :main                 om-cookbook.cookbook
                               :source-map-timestamp true
                               :asset-path           "cookbook"
                               :output-to            "resources/public/cookbook/cookbook.js"
                               :output-dir           "resources/public/cookbook"
                               :parallel-build       true
                               :recompile-dependents true
                               :verbose              false
                               :foreign-libs         [{:provides ["cljsjs.codemirror.addons.closebrackets"]
                                                       :requires ["cljsjs.codemirror"]
                                                       :file     "resources/public/codemirror/closebrackets-min.js"}
                                                      {:provides ["cljsjs.codemirror.addons.matchbrackets"]
                                                       :requires ["cljsjs.codemirror"]
                                                       :file     "resources/public/codemirror/matchbrackets-min.js"}]}}]}

  :profiles {
             :dev {:source-paths ["src/dev"]
                   :repl-options {:init-ns user
                                  :port    7001}
                   }
             })
