
def main(data):
	num_valid = 0
	for line in data.split("\n"):
		if is_valid(line):
			num_valid += 1
	return num_valid



def is_valid(passphrase):
	data = {}
	for word in passphrase.split():
		if word in data:
			return False
		data[word] = True
	return True


def get_input():
    print("Give your input. EOF (CTRL+D) to end:")
    data = []
    while True:
        try:
            data.append(raw_input())
        except EOFError:
            break
    return "\n".join(data)

if __name__ == '__main__':
    while True:
        data = get_input()
        checksum = main(data)
        print "Num valid is: {}".format(checksum)
