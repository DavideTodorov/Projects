import sys

import deck
import hand
import chip

playing = True


def take_bet(chips):
    while True:
        try:
            chips_to_bet = int(input('How many chips do you want to bet? '))
        except ValueError:
            print('Please provide a valid number')
        else:
            if chips_to_bet > chips.total:
                print("You don't have enough chips! You have {}".format(chips.total))
            else:
                chips.bet = chips_to_bet
                break


def hit(cards_deck, hand):
    single_card = cards_deck.deal()
    hand.add_card(single_card)
    hand.adjust_for_ace()


def hit_or_stand(cards_deck, hand):
    global playing

    while True:
        user_command = input('\nHit or Stand? Enter h or s ')

        if user_command[0].lower() == 'h':
            hit(cards_deck, hand)
        elif user_command[0].lower() == 's':
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


if __name__ == "__main__":

    while True:
        print('\nWelcome to Blackjack!')

        cards_deck = deck.Deck()
        cards_deck.shuffle()

        player_hand = hand.Hand()
        player_hand.add_card(cards_deck.deal())
        player_hand.add_card(cards_deck.deal())

        dealer_hand = hand.Hand()
        dealer_hand.add_card(cards_deck.deal())
        dealer_hand.add_card(cards_deck.deal())

        player_chips = chip.Chip()
        take_bet(player_chips)

        show_some(player_hand, dealer_hand)

        while playing:
            hit_or_stand(cards_deck, player_hand)

            show_some(player_hand, dealer_hand)

            if player_hand.value > 21:
                player_bust(player_hand, dealer_hand, player_chips)
                break

            if player_hand.value <= 21:
                while dealer_hand.value < player_hand.value:
                    hit(cards_deck, dealer_hand)

                show_all(player_hand, dealer_hand)

                if dealer_hand.value > 21:
                    dealer_bust(player_hand, dealer_hand, player_chips)
                elif dealer_hand.value > player_hand.value:
                    dealer_wins(player_hand, dealer_hand, player_chips)
                elif dealer_hand.value < player_hand.value:
                    player_wins(player_hand, dealer_hand, player_chips)
                else:
                    push(player_hand, dealer_hand)

            print(f"\n Player's total chips: {player_chips.total}")

            new_game = ''

            while new_game not in ('y', 'n'):
                new_game = input('Would you like to play again? y/n? ')

                if new_game[0].lower() == 'y':
                    playing = True
                    break
                elif new_game[0].lower() == 'n':
                    print('Thank you for playing! Bye!')
                    sys.exit()
                else:
                    print('You entered wrong command!')
