package jpabook.jpashop1.study;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamPractice {
    public static void main(String[] args) {

        /*
         * 1. 기본 ArrayList 만들기
         * */

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        System.out.printf("원본 리스트 : " + list);
        // [1, 2, 3, 4]

        /*
         * 2. 각 숙자에 2를 곱해서 새로운 리스트 반환
         */
        List<Integer> list2 = new ArrayList<>();

        for (Integer num : list) {
            list2.add(num * 2);
        }

        System.out.printf("for문 결과 : " + list2);
        // [2, 4, 6, 8]

        /*
         * 3. Java8의 Stream() + map() 사용하기
         * -map() : 리스트의 요소를 하나씩 꺼내서 반환하는 기능
         * - collect() : 다시 리스트로 모아줌
         * - 최종적으로는 for 문과 똑같은 결과
         * */

        List<Integer> list3 = list.stream().map(n -> n * 2).collect(Collectors.toList());
        System.out.printf("stream 결과 : " + list3);
        // [2, 4, 6, 8]

    }
}
