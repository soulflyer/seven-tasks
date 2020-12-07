(ns seven-tasks.click-counter
  (:require
   [reagent.core :as r]
   [seven-tasks.routes :as routes]))

(defn page []
  (let [clicks (r/atom 0)]
    (fn []
      [:span.main
       [:h1 "Task 1, Click Counter"]
       [:p "There have been " @clicks " clicks"]
       [:input {:type "button"
                :value "Click me"
                :on-click #(swap! clicks inc)}]
       [:p [:a {:href (routes/path-for :index)} "Back to the list of tasks"]]])))
