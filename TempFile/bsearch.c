//
//  lsearch.c
//
//  Created by Nerrtica on 2015. 3. 19..
//  Copyright (c) 2015년 Nerrtica. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

void readList (FILE *inputFile, int *list, int listLength);
void readQuery (FILE *inputFile, int **query, int queryNum);
int binarySearch (int *list, int start, int end, int num, char *isFound);

int main(int argc, const char * argv[]) {
    FILE *inputFile, *outputFile;
    int listLength, *list, queryNum, **query, start, end, count, i, j;
    char isStartFound, isEndFound;
    
    inputFile = fopen(argv[1], "r");
    
    fscanf(inputFile, "%d", &listLength);
    list = (int *)malloc(sizeof(int) * listLength);
    readList(inputFile, list, listLength);
    
    fscanf(inputFile, "%d", &queryNum);
    query = (int **)malloc(sizeof(int *) * queryNum);
    for (i = 0; i < queryNum; i++) {
        query[i] = (int *)malloc(sizeof(int) * 2);
    }
    readQuery(inputFile, query, queryNum);
    
    fclose(inputFile);
    outputFile = fopen(argv[2], "w");
    
    for (i = 0; i < queryNum; i++) {
        start = binarySearch(list, 0, listLength - 1, query[i][0], &isStartFound);
        end = binarySearch(list, 0, listLength - 1, query[i][1], &isEndFound);
        if (isEndFound == 0) { end--; }
        
        if (isStartFound == 1) {
            for (j = start - 1; j >= 0; j--) {
                if (list[j] == list[start]) {
                    start = j;
                }
            }
        }
        if (isEndFound == 1) {
            for (j = end + 1; j < listLength; j++) {
                if (list[j] == list[end]) {
                    end = j;
                }
            }
        }

        count = end - start + 1;
        fprintf(outputFile, "%d\n", count);
    }
    
    fclose(outputFile);
    
    return 0;
}

void readList (FILE *inputFile, int *list, int listLength) {
    int i;
    
    for (i = 0; i < listLength; i++) {
        fscanf(inputFile, "%d", &list[i]);
    }
}

void readQuery (FILE *inputFile, int **query, int queryNum) {
    int i, j;
    
    for (i = 0; i < queryNum; i++) {
        for (j = 0; j < 2; j++) {
            fscanf(inputFile, "%d", &query[i][j]);
        }
    }
}

int binarySearch (int *list, int start, int end, int num, char *isFound) {
    int mid;

    if (start > end) {
        *isFound = 0;
        if (list[end] > num) { return end; }
        else { return end + 1; }
    }

    mid = (start + end) / 2;
    if (list[mid] == num) {
        *isFound = 1;
        return mid;
    } else if (list[mid] < num) {
        return binarySearch(list, mid + 1, end, num, isFound);
    } else if (list[mid] > num) {
        return binarySearch(list, start, mid - 1, num, isFound);
    }

    return 0;
}