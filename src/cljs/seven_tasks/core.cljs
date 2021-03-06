(ns seven-tasks.core
  (:require
   [accountant.core :as accountant]
   [clerk.core :as clerk]
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [reagent.session :as session]
   [reitit.frontend :as reitit]
   [seven-tasks.about :as about]
   [seven-tasks.click-counter :as click-counter]
   [seven-tasks.flight-booker :as flight-booker]
   [seven-tasks.home :as home]
   [seven-tasks.routes :as routes]
   [seven-tasks.temperature-converter :as temperature-converter]))

(defn task-page []
  (fn []
    (let [routing-data (session/get :route)
          task (get-in routing-data [:route-params :task-id])]
      [:span.main
       [:h1 (str "Task " task " of seven-tasks")]
       [:p [:a {:href (routes/path-for :index)} "Back to the list of tasks"]]])))


;; -------------------------
;; Translate routes -> page components

(defn page-for [route]
  (case route
    :index #'home/page
    :about #'about/page
    :task #'task-page
    :click-counter #'click-counter/page
    :temperature-converter #'temperature-converter/page
    :flight-booker #'flight-booker/page))


;; -------------------------
;; Page mounting component

(defn current-page []
  (fn []
    (let [page (:current-page (session/get :route))]
      [:div
       [:header
        [:p [:a {:href (routes/path-for :index)} "Home"] " | "
         [:a {:href (routes/path-for :about)} "About seven-tasks"]]]
       [page]
       [:footer
        [:p "seven-tasks was generated by the "
         [:a {:href "https://github.com/reagent-project/reagent-template"} "Reagent Template"] "."]]])))

;; -------------------------
;; Initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (clerk/initialize!)
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (let [match (reitit/match-by-path routes/router path)
             current-page (:name (:data  match))
             route-params (:path-params match)]
         (reagent/after-render clerk/after-render!)
         (session/put! :route {:current-page (page-for current-page)
                               :route-params route-params})
         (clerk/navigate-page! path)
         ))
     :path-exists?
     (fn [path]
       (boolean (reitit/match-by-path routes/router path)))})
  (accountant/dispatch-current!)
  (mount-root))
