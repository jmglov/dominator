(ns dominator.views
  (:require [re-frame.core :as rf]
            [dominator.cards :as cards]
            [dominator.events :as events]
            [dominator.subs :as subs]))

(defn gen-key []
  (gensym "key-"))

(defn draw-card [{:keys [card]}]
  [:span {:key (gen-key)
          :style {:padding "0px 5px 0px 5px"}}
   (get-in cards/cards [card :name])])

(defn draw-player [n players]
  (let [{:keys [deck hand discard]} (players n)]
    [:div {:key (gen-key)}
     [:span (str "Deck: " (count deck))]
     [:div {:style {:display "flex"}}
      [:span "Hand:"]
      (map draw-card hand)]
     [:div {:style {:display "flex"}}
      [:span "Discard:"]
      (draw-card (first discard))
      (when-not (empty? discard)
        [:span (str "(" (dec (count discard)) ")")])]]))

(defn main-panel []
  (let [players (rf/subscribe [::subs/players])
        current-player (rf/subscribe [::subs/current-player])
        supply (rf/subscribe [::subs/supply])
        trash (rf/subscribe [::subs/trash])
        game-started? (rf/subscribe [::subs/game-started?])]
    [:div
     [:h1 "Dominator"]
     [:h2 "Player 1"]
     (when-not (empty? @players) (draw-player 0 @players))
     [:h2 "Supply"]
     [:h2 "Trash"]
     [:h2 "Player 2"]
     (when-not (empty? @players) (draw-player 1 @players))
     [:button {:on-click #(rf/dispatch [::events/start-game])
               :style {:display (if @game-started? "none" "block")}}
      "Start game"]]))
