def main():
    f = open("input3.txt")
    fileContent = f.read()
    grid = list()
    total = 0

    for row in fileContent.split("\n"):
        grid.append(row)

    for i in range(0, len(grid)):
        for k in range(0, len(grid[0])):
            if grid[i][k] == "*": # MAKE LIST OF NUMS IN ABOVE CURRENT AND BOTTOM ROW. GET GRID OF INDICES AROUND STAR. CHECK IF GRID INDICES ARE ENCAPSULATED BY ANY NUMBER INDICES AND IF SO ADD NUM TO A LIST TO MULTIPLY TOG
                squaresToCheck = get3x3(grid,i,k)
                rowsToScan = getRows(grid,i)
                rowNums = list()
                finalNums = list()
                for row in rowsToScan:
                    character = 0
                    firstDigit = True
                    startIndex = 0
                    endIndex = 0
                    for character in range(0, len(grid[0])):
                        if grid[row][character].isdigit():
                            if firstDigit:
                                startIndex = character
                                endIndex = character
                                firstDigit = False
                            else:
                                endIndex = character
                                if character == 139:
                                    rowNums.append((row, startIndex,endIndex))
                                    firstDigit = True
                        elif not firstDigit:
                            rowNums.append((row,startIndex,endIndex))
                            firstDigit = True

                        else:
                            firstDigit = True

                for num in rowNums:
                    flagOnce = False
                    for square in squaresToCheck:
                        if not flagOnce:
                            if num[0] == square[0] and ( num[1] <= square[1] <= num[2]):
                                finalNums.append(int(grid[num[0]][num[1]:num[2]+1]))
                                flagOnce = True

                if len(finalNums) == 2:
                    total += (finalNums[0] * finalNums[1])
                    finalNums = list()
                    rowNums = list()
    print(total)

def get3x3(grid, row, col):
    maxRow = len(grid)
    maxCol = len(grid[0])
    table3x3 = list()
    for i in range(row-1, row+2):

        for k in range(col-1, col+2):

            if isCellValid(i,k,maxRow,maxCol):
                table3x3.append((i,k))
    return table3x3

def isCellValid(row, col, maxRow, maxCol):
    return 0 <= row <= maxRow - 1 and col >= 0 and col <= maxCol-1


def getRows(grid,row):
    rows = list()
    maxRow = len(grid)
    for i in range(row-1, row+2):
        if isRowValid(i,maxRow):
            rows.append(i)

    return rows


def isRowValid(row,maxRow):
    return row >= 0 and row < maxRow


if __name__ == "__main__":
    main()