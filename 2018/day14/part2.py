
def add_recipe(recipes, recipe):
    recipes.append(recipe)

def process_batch(recipes, last_indexes):
    last_recipes = [recipes[idx] for idx in last_indexes]
    tmp_score = sum(last_recipes)
    recipes_to_add = [1, tmp_score % 10] if tmp_score > 9 else [tmp_score]
    
    #Mutating here on
    for recipe in recipes_to_add:
        add_recipe(recipes, recipe)
    
    new_recipes_length = len(recipes)
    new_indexes = [
            (last_recipes[0] + last_indexes[0] + 1) % new_recipes_length,
            (last_recipes[1] + last_indexes[1] + 1) % new_recipes_length
    ]
    return (recipes, new_indexes)


class StopException(Exception):
    pass


def process_all(recipes, indexes, stop_fn):
    while True:
        last_recipes = [recipes[idx] for idx in indexes]
        tmp_score = sum(last_recipes)
        recipes_to_add = [1, tmp_score % 10] if tmp_score > 9 else [tmp_score]
        
        #Mutating here on
        for recipe in recipes_to_add:
            add_recipe(recipes, recipe)
            stop_fn(recipes)
        
        new_recipes_length = len(recipes)
        indexes = [
                (last_recipes[0] + indexes[0] + 1) % new_recipes_length,
                (last_recipes[1] + indexes[1] + 1) % new_recipes_length
        ]


def main():
    START_RECIPES = [3, 7]
    START_INDEXES = [0, 1]

    input_data = (7, 6, 0, 2, 2, 1)
#    input_data = (9,2,5,1,0)

    def stop_function(recipes):
        if tuple(recipes[-6:]) == input_data:
            raise StopException("answer is", len(recipes) - 6, recipes[-10:])
    try:
        process_all(START_RECIPES, START_INDEXES, stop_function)
    except StopException as e:
        print(e)


main()
