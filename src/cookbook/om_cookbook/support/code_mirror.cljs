(ns om-cookbook.support.code-mirror
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs.reader :as r]
            [cljs.pprint :refer [pprint]]
            [cljsjs.codemirror]
            [cljsjs.codemirror.mode.clojure]
            [cljsjs.codemirror.addons.matchbrackets]
            [cljsjs.codemirror.addons.closebrackets]))

(def cm-clojure-opts
  {:fontSize          8
   :lineNumbers       true
   :matchBrackets     true
   :autoCloseBrackets true
   :indentWithTabs    false
   :mode              {:name "clojure"}})

(defn pprint-src
  "Pretty print src for CodeMirro editor.
  Could be included in textarea->cm"
  [s]
  (-> s
      r/read-string
      pprint
      with-out-str))

(defn textarea->cm
  "Decorate a textarea with a CodeMirror editor given an node and options."
  [node options]
  (js/CodeMirror
    #(.replaceChild (.-parentNode node) % node)
    (clj->js options)))

(defui CodeMirror
  Object
  (componentDidMount [this]
    (let [{:keys [value on-change options]} (om/props this)
          node (js/ReactDOM.findDOMNode (om/react-ref this "textarea"))
          src (pprint-src value)
          cm (textarea->cm node (assoc (or options cm-clojure-opts) :value src))]
      (.on cm "change" #(if (fn? on-change) (on-change (.getValue %))))
      (om/update-state! this assoc :cm cm)))

  (render [_]
    (dom/div nil
      (dom/textarea #js {:ref "textarea"}))))

(def code-mirror (om/factory CodeMirror))

