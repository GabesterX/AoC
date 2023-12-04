def main():
    f = open("input3.txt")
    fileContent = f.read()
    grid = list()
    total = 0

    for row in fileContent.split("\n"):
        grid.append(row)

    for i in range(0, len(grid)):

        firstDigit = True
        startIndex = 0
        endIndex = 0

        for k in range(0, len(grid[i])):
            print(grid[i][k])




            if grid[i][k].isdigit():
                if firstDigit:
                    startIndex = k
                    endIndex = k
                    firstDigit = False
                else:
                    endIndex = k
                    if (k == 139):
                        firstDigit = True
                        addNum = False
                        for w in range(startIndex, endIndex + 1):
                            if checkNeighbours(grid, i, w):
                                addNum = True

                        if addNum:
                            total = total + int(grid[i][startIndex:endIndex + 1])

                        addNum = False

            elif not firstDigit:

                firstDigit = True
                addNum = False
                for w in range(startIndex, endIndex+1):
                    if checkNeighbours(grid, i, w):
                        addNum = True

                if addNum:

                    total = total + int(grid[i][startIndex:endIndex + 1])

                addNum = False


            else:
                firstDigit = True
                addNum = False






    print(total)


def checkNeighbours(grid, row, col):
    for i in range(row-1, row+2):

        for k in range(col-1, col+2):
            if isValid(i,k, len(grid), len(grid[0])):

                if not grid[i][k].isdigit() and not grid[i][k] == ".":

                    return True
    return False

def isValid(row, col, maxRow, maxCol):
    return (row >= 0) and (col >= 0) and (row < maxRow-1) and (col < maxCol-1)


if __name__ == "__main__":
    main()