(ns reagent-example-app.weather
  (:require [reagent.core :as reagent]
            [reagent-example-app.corecomponents :as cc]
            [goog.string :as gstring]
            [goog.string.format :as gformat]
            [ajax.core :refer [GET]]))


(def api-key "444112d540b141913a9c1ee6d7f495fa")


(defn weather-panel [weather]
  (if-not weather
    [:h1 "No weather data available"]
    (let [current-weather (get-in weather [:weather 0])]
      [:div
       [:h1 (str "Weather in " (:name weather))]
       [:h2 (str (get-in weather [:main :temp] " °C"))
        [:img {:src (str "http://openweathermap.org/img/w/" (:icon current-weather) ".png")}]]
       [:p (weather (:description current-weather))]])))


(defn handle-server-response! [state response]
  (println response)
  (swap! state assoc :weather response :error nil))

(defn handle-server-error! [state {:keys [status status-text]}]
  (swap! state assoc :weather nil :error status-text))

(defn fetch-weather! [state]
  (let [city (:city @state)]
    (GET (gstring/format "http://api.openweathermap.org/data/2.5/weather?q=%s,de&appid=%s&units=metric" city api-key)
         {:handler (partial handle-server-response! state)
          :error-handler (partial handle-server-error! state)
          :response-format :json
          :keywords? true})))

(defn weather-view [& {:keys [initial-city] :or {initial-city "Hamburg"}}]
  (let [state (reagent/atom {:city initial-city})]
    (fetch-weather! state)
    (fn []
      (let [{:keys [city weather error]} @state]
        [:div
         [:h1 "Current Weather"]
         [:input {:type "text" :focus true :value city
                  :on-change #(swap! state assoc :city (-> % .-target .-value))}]
         [cc/button "Load" :enabled (pos? (count city)) :on-click #(fetch-weather! state)]
         (when weather [weather-panel weather])
         (when error [:div.Red (str "Error: " error)])]))))

