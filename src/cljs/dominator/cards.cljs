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
           :cost 0}

   :cellar {:name "Cellar"
            :type #{:action}
            :cost 2
            :actions [{:plus-actions 1}
                      {:discard [0 :num-in-hand]}
                      {:draw :num-discarded}]
            :description "Discard any number of cards. +1 card per card discarded."}
   :market {:name "Market"
            :type #{:action}
            :cost 2
            :actions [{:plus-cards 1}
                      {:plus-actions 1}
                      {:plus-buys 1}
                      {:plus-money 1}]}
   :militia {:name "Militia"
             :type #{:action :attack}
             :cost 2
             :actions [{:plus-money 1}]}
   :mine {:name "Mine"
          :type #{:action}
          :cost 2
          :actions [{:plus-actions 1}
                    {:discard [0 :num-in-hand]}
                    {:draw :num-discarded}]}
   :moat {:name "Moat"
          :type #{:action}
          :cost 2
          :actions [{:plus-actions 1}
                    {:discard [0 :num-in-hand]}
                    {:draw :num-discarded}]}
   :remodel {:name "Remodel"
             :type #{:action}
             :cost 2
             :actions [{:plus-actions 1}
                       {:discard [0 :num-in-hand]}
                       {:draw :num-discarded}]}
   :smithy {:name "Smithy"
            :type #{:action}
            :cost 2
            :actions [{:plus-actions 1}
                      {:discard [0 :num-in-hand]}
                      {:draw :num-discarded}]}
   :village {:name "Village"
             :type #{:action}
             :cost 2
             :actions [{:plus-actions 1}
                       {:discard [0 :num-in-hand]}
                       {:draw :num-discarded}]}
   :woodcutter {:name "Woodcutter"
                :type #{:action}
                :cost 2
                :actions [{:plus-actions 1}
                          {:discard [0 :num-in-hand]}
                          {:draw :num-discarded}]}
   :workshop {:name "Workshop"
              :type #{:action}
              :cost 2
              :actions [{:plus-actions 1}
                        {:discard [0 :num-in-hand]}
                        {:draw :num-discarded}]}})

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
  {:deck []
   :hand []
   :discard (concat (repeat 7 {:card :copper, :face-up? true})
                    (repeat 3 {:card :estate, :face-up? true}))
   :in-play []})

(def trash
  [])
