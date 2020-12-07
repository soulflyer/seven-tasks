(ns seven-tasks.routes
  (:require
   [reitit.frontend :as reitit]))

(def router
  (reitit/router
    [["/" :index]
     ["/tasks"
      ["/:task-id" :task]]
     ["/click-counter" :click-counter]
     ["/temperature-converter" :temperature-converter]
     ["/about" :about]]))

(defn path-for [route & [params]]
  (if params
    (:path (reitit/match-by-name router route params))
    (:path (reitit/match-by-name router route))))
