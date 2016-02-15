(ns reagent-example-app.core
  (:require [reagent.core :as reagent]
            [reagent-example-app.corecomponents :as cc]
            [reagent-example-app.password :as pw]
            [reagent-example-app.weather :as we]
            [reagent-example-app.chart :as ch]))

(enable-console-print!)

(defn render-application-view [current-view-id]
  (case current-view-id
    "passwordView"  [pw/password-view]
    "weatherView"   [we/weather-view]
    "chartView"     [ch/chart-view]
    [pw/password-view])
  )

(defn application []
  (let [current-view-id (reagent/atom "passwordView")]
    (fn []
      (let [application-view-class-names (str "ApplicationView ApplicationView-" @current-view-id)]
        [:div
         [cc/navigation-bar @current-view-id #(reset! current-view-id %)
          [{:label "Password Form" :view-id "passwordView"}
           {:label "Weather Report" :view-id "weatherView"}
           {:label "Chart Example" :view-id "chartView"}]]
         [:div {:class application-view-class-names}
          (render-application-view @current-view-id)]]))))


(reagent/render-component [application]
                          (. js/document (getElementById "app")))

