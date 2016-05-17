//
//  factorial.c
//
//  Created by Nerrtica
//  Copyright (c) Nerrtica. All rights reserved.
//

#include <stdio.h>

int fact (int n);

int main () {
	int n;

	while (1) {
		printf("몇 factorial의 값을 구하시겠습니까? : ");
		scanf("%d", &n);

		if (n == -1) { break; }
		else if (n < 0) {
			printf("잘못된 입력값입니다. 양수를 입력해주세요.\n");
			continue;
		}
		else {
			printf("%d! : %d\n", n, fact(n));
		}
	}

	return 0;
}

int fact (int n) {
	if (n == 0) {
		return 1;
	}
	else {
		return n * fact(n - 1);
	}
}
/* Diff Test */