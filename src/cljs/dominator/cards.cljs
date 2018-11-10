(ns dominator.cards)

(def cards
  {:gold {:name "Gold"
          :type #{:treasure}
          :value 3
          :cost 6}
   :silver {:name "Silver"
            :type #{:treasure}
            :value 2
            :cost 3}
   :copper {:name "Copper"
            :type #{:treasure}
            :value 1
            :cost 0}

   :province {:name "Province"
              :type #{:victory}
              :vp 6
              :cost 8}
   :duchy {:name "Duchy"
           :type #{:victory}
           :vp 3
           :cost 5}
   :estate {:name "Estate"
            :type #{:victory}
            :vp 1
            :cost 2}
   :curse {:name "Curse"
           :type #{:victory}
           :vp -1
           :cost 0}})

(def supply
  {:treasure [{:card :gold, :count 30}
              {:card :silver, :count 40}
              {:card :copper, :count 60}]
   :victory [{:card :province, :count 12}
             {:card :duchy, :count 12}
             {:card :estate, :count 24}
             {:card :curse, :count 30}]
   :main []
   :trash []})

(def player
  {:deck (concat (repeat 7 :copper) (repeat 3 :estate))
   :hand []
   :discard []})

(def trash
  [])
