def take_bet(chips):
    while True:
        try:
            chips_to_bet = int(input('How many chips do you want to bet?'))
        except ValueError:
            print('Please provide a valid number')
        else:
            if chips_to_bet > chips.total:
                print("You don't have enough chips! You have {}".format(chips.total))
            else:
                chips.bet = chips_to_bet
                break


def hit(deck, hand):
    single_card = deck.deal()
    hand.add_card(single_card)
    hand.adjust_for_ace()


def hit_or_stand(deck, hand):
    global playing

    while True:
        user_command = input('Hit or Stand? Enter h or s')

        if user_command[0].lower() == 'h':
            hit(deck, hand)
        elif user_command[0].lower() == 'h':
            print("Player stands Dealer's turn")
            playing = False
        else:
            print('You have entered wrong command. Please enter h or s')
            continue

        break


def show_some(player, dealer):
    print("\n Dealer's hand: ")
    print('First card hidden')
    print(dealer.cards[1])

    print("\n Player's hand: ", *player.cards, sep='\n')


def show_all(player, dealer):
    print("\n Dealer's hand: ", *player.cards, sep='\n')
    print(f"Value of Dealer's hand is {dealer.value}")

    print("\n Player's hand: ", *player.cards, sep='\n')
    print(f"Value of Player's hand is {player.value}")


def player_bust(player, dealer, chips):
    print('Bust Player!')
    chips.lose_bet()


def player_wins(player, dealer, chips):
    print('Player Wins!')
    chips.win_bet()


def dealer_bust(player, dealer, chips):
    print('Dealer is Busted!')
    chips.win_bet()


def dealer_wins(player, dealer, chips):
    print('Dealer Wins!')
    chips.win_bet()


def push(player, dealer):
    print('Dealer and Player are tie! Push')
