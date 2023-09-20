from IPython.display import clear_output
import boardManager
import gameLogicManager
import utils

# Global variables
user1_symbol = ''
user2_symbol = ''
default_symbol_for_board = [' - ']
board = default_symbol_for_board * 9


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
    row_chosen = ''
    while row_chosen not in ['row1', 'row2', 'row3']:
        row_chosen = input(f'{user_name}, please choose row (row1, row2, row3): ')

    col_chosen = ''
    while col_chosen not in ['1', '2', '3']:
        col_chosen = input(f'{user_name}, please choose column (1, 2, 3): ')

    chosen_board_index = utils.get_index_from_row_and_col(row_chosen, col_chosen)

    if board[chosen_board_index] == default_symbol_for_board:
        get_user_choice(user_name)

    return chosen_board_index


def game_start():
    global board
    user_playing_on_current_turn = 'User1'
    user_playing_on_next_turn = 'User1'
    board_index = -1

    while not gameLogicManager.is_there_win_condition(board):
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


def initialize_game():
    clear_output()
    get_users_symbol_preference()
    game_start()


initialize_game()
