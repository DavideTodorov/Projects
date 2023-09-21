from IPython.display import clear_output
import boardManager
import gameLogicManager
import utils

# Global variables
user1_symbol = ''
user2_symbol = ''
index_range = list(range(1, 10))
board = index_range


def get_users_symbol_preference():
    global user1_symbol
    global user2_symbol
    user1_choice = ''
    while user1_choice not in ['X', 'O']:
        user1_choice = input('Please choose X or O for User1: ')

    if user1_choice == 'X':
        user1_symbol = 'X'
        user2_symbol = 'O'
    else:
        user1_symbol = 'O'
        user2_symbol = 'X'


def get_user_choice(user_name):
    global board
    chosen_board_index = -1
    while chosen_board_index not in index_range:
        try:
            chosen_board_index = int(input(f'{user_name}, please choose index in range (1,9): '))
        except ValueError:
            pass

    board_index = int(chosen_board_index) - 1
    if board[board_index] not in index_range:
        get_user_choice(user_name)

    return board_index


def game_start():
    global board
    user_playing_on_current_turn = 'User1'
    user_playing_on_next_turn = 'User1'
    board_index = -1

    has_someone_won = False
    while not has_someone_won:
        if user_playing_on_next_turn == 'User1':
            user_playing_on_current_turn = 'User1'
            user_playing_on_next_turn = 'User2'
            board_index = get_user_choice(user_playing_on_current_turn)
        elif user_playing_on_next_turn == 'User2':
            user_playing_on_current_turn = 'User2'
            user_playing_on_next_turn = 'User1'
            board_index = get_user_choice(user_playing_on_current_turn)

        symbol = utils.get_symbol(user_playing_on_current_turn, user1_symbol, user2_symbol)
        board = boardManager.update_board(board, board_index, symbol)
        boardManager.print_board(board)
        has_someone_won = gameLogicManager.is_there_win_condition(board)

    if has_someone_won:
        print(f'Congrats, {user_playing_on_current_turn}, you won the game!')


def initialize_game():
    clear_output()
    get_users_symbol_preference()
    boardManager.print_board(board)
    game_start()


initialize_game()
