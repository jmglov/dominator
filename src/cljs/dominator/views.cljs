(ns dominator.views
  (:require [re-frame.core :as rf]
            [dominator.cards :as cards]
            [dominator.events :as events]
            [dominator.subs :as subs]))

(defn gen-key []
  (gensym "key-"))

(defn group-cards-by [key cards]
  (group-by (fn [{:keys [card]}] (get-in cards/cards [card key])) cards))

(defn render-card
  ([card]
   (render-card card nil))
  ([{:keys [card]} turn-phase]
   [:span (merge {:key (gen-key)
                  :style {:padding "0px 5px 0px 5px"}}
                 (when (cards/active? card turn-phase)
                   {:class "active-card"}))
    (get-in cards/cards [card :name])]))

(defn render-pile [card]
  [:div {:key (gen-key)
         :style {:display "flex"}}
   (render-card card)
   [:span (str "(" (:count card) ")")]])

(defn render-hand [turn-phase cards]
  (->> cards
       (group-cards-by :type)
       (map second)
       flatten
       (map render-card)))

(defn render-player [player-num current-player turn-phase players]
  (let [current-player? (= current-player player-num)]
    [:div {:class (str "outlined" (when current-player? " current-player"))}
     [:h3 (str "Player " (inc player-num))]
     (when-not (empty? players)
       (let [{:keys [deck hand discard]} (players player-num)]
         [:div {:key (gen-key)}
          [:span (str "Deck: " (count deck))]
          [:div {:style {:display "flex"}}
           [:span "Hand:"]
           (render-hand turn-phase hand)]
          [:div {:style {:display "flex"}}
           [:span "Discard:"]
           (render-card (first discard))
           (when-not (empty? discard)
             [:span (str "(" (dec (count discard)) ")")])]]))
     (when current-player?
       [:button {:on-click #(rf/dispatch [::events/next-phase])}
        (str "Skip " (name turn-phase))])]))

(defn render-row [cards]
  [:div {:key (gen-key)
         :style {:display "flex"}}
   (map render-pile cards)])

(defn render-supply [cards]
  (->> cards
       (group-cards-by :cost)
       (sort-by first)
       reverse
       (map (comp render-row second))))

(defn main-panel []
  (let [players (rf/subscribe [::subs/players])
        current-player (rf/subscribe [::subs/current-player])
        supply (rf/subscribe [::subs/supply])
        trash (rf/subscribe [::subs/trash])
        game-started? (rf/subscribe [::subs/game-started?])
        turn-phase (rf/subscribe [::subs/turn-phase])]
    [:div
     [:h1 "Dominator"]
     (render-player 0 @current-player @turn-phase @players)
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
     (render-player 1 @current-player @turn-phase @players)
     [:button {:on-click #(rf/dispatch [::events/start-game])
               :style {:display (if (true? @game-started?) "none" "block")}}
      "Start game"]]))
