def get_index_from_row_and_col(row, col):
    row_index = int(row[-1])
    index = (row_index * int(col)) - 1
    # TODO think of correct algorithm
    return index


def get_symbol(user_name, user1_symbol, user2_symbol):
    if user_name == 'User1':
        return user1_symbol
    else:
        return user2_symbol
