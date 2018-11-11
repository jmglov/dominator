(ns dominator.views
  (:require [re-frame.core :as rf]
            [dominator.cards :as cards]
            [dominator.events :as events]
            [dominator.subs :as subs]))

(defn gen-key []
  (gensym "key-"))

(defn render-card [{:keys [card]}]
  [:span {:key (gen-key)
          :style {:padding "0px 5px 0px 5px"}}
   (get-in cards/cards [card :name])])

(defn render-pile [card]
  [:div {:key (gen-key)
         :style {:display "flex"}}
   (render-card card)
   [:span (str "(" (:count card) ")")]])

(defn render-player [n players]
  (let [{:keys [deck hand discard]} (players n)]
    [:div {:key (gen-key)}
     [:span (str "Deck: " (count deck))]
     [:div {:style {:display "flex"}}
      [:span "Hand:"]
      (map render-card hand)]
     [:div {:style {:display "flex"}}
      [:span "Discard:"]
      (render-card (first discard))
      (when-not (empty? discard)
        [:span (str "(" (dec (count discard)) ")")])]]))

(defn render-row [cards]
  [:div {:style {:display "flex"}}
   (map render-pile cards)])

(defn render-supply [cards]
  (->> cards
       (group-by (fn [{:keys [card]}] (get-in cards/cards [card :cost])))
       (sort-by first)
       reverse
       (map second)
       (map render-row)))

(defn main-panel []
  (let [players (rf/subscribe [::subs/players])
        current-player (rf/subscribe [::subs/current-player])
        supply (rf/subscribe [::subs/supply])
        trash (rf/subscribe [::subs/trash])
        game-started? (rf/subscribe [::subs/game-started?])]
    [:div
     [:h1 "Dominator"]
     [:div {:class "outlined"}
      [:h3 "Player 1"]
      (when-not (empty? @players) (render-player 0 @players))]
     [:div {:class "outlined"}
      [:h3 "Supply"]
      [:div
       [:div {:style {:display "flex"}}
        (map render-pile (:treasure @supply))]
       [:div {:style {:display "flex"}}
        (map render-pile (:victory @supply))]
       [:div
        (render-supply (:main @supply))]]]
     [:div {:class "outlined"}
      [:h3 "Trash"]]
     [:div {:class "outlined"}
      [:h3 "Player 2"]
      (when-not (empty? @players) (render-player 1 @players))]
     [:button {:on-click #(rf/dispatch [::events/start-game])
               :style {:display (if (true? @game-started?) "none" "block")}}
      "Start game"]]))
