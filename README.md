# 블로그 검색 서비스 개발

## 프로젝트 설명
각종 포털의 블로그 검색 API 를 이용하여 블로그 검색, 인기 검색어 목록 조회 api 기능을 제공합니다.

## 실해 방법
search-service/jar 경로에서 아래 명령어 호출

```
java -jar search-service-0.0.1-SNAPSHOT.jar 
```



## API 가이드
### 블로그 검색 API
  + api desc : 블로그에 대한 페이징 정보를 조회해옵니다. 검색 시 키워드에 따라 카운트가 1 증가합니다. searchSourceType(현재 제공: KAKAO(default), NAVER) 에 따라 각 사에서 제공하는 
  API 로 정보르 가져옵니다. 조회 실패시 타 블로그 검색 API를 이용합니다.
  + request url : http://127.0.0.1:17001/search
  + method : POST
  + 카카오
    + request body
    ```
    {
      "keyword" : "서현",
      "searchSourceType":"KAKAO", // 생략가능
      "sort" : "recency", // recency(최신순), accuracy(정확도순) 
      "page" : 2,
      "size" : 10
    }
    ```
    + response body
    ```
    {
      "meta": {
          "total_count": 490,
          "pageable_count": 442,
          "_end": false
      },
      "documents": [
          {
              "title": "Chapter 6 Coming Back",
              "contents": "child apart! “Mommy, you’re crying!” Dan&#39;s little hand reached to Sherry, trying to wipe out the tears for her, “Don&#39;t cry, mommy. l&#39;ÌÍ be good, l&#39;lÍ protect you.” She was downhearted at that...",
              "url": "https://ttsnovel.com/https://novelebook.com/a-moment-in-destiny/r1189.html",
              "date_time": null
          },
          {
              "title": "jkrs-22-412.pdf",
              "contents": "여타l 껏 얀려진 쉬값띠인 所見， 즉 勝뼈의 上해5 에 감在하거 냐， 꾀한 에 ~. 後 &#34;Jj 홈影 그라고 <b>ìí</b>!i!r，i 와 毛髮 상E 界絲 ( fluid • hair level ) 을 보이 는 경우는 少數에서였다고 행연하고 있다...",
              "url": "https://synapse.koreamed.org/func/download.php?path=L2hvbWUvdmlydHVhbC9rYW1qZS9zeW5hcHNlL3VwbG9hZC9TeW5hcHNlRGF0YS9QREZEYXRhLzEwMTZqa3JzL2prcnMtMjItNDEyLnBkZg==&filename=amtycy0yMi00MTIucGRm",
              "date_time": null
          },
          {
              "title": "행복기숙사 지원사업 신청안내서_내지최종.pdf",
              "contents": "® M0Mo y ¯ 1 ¯ ° ±B,s ³ ² ´} 1 ¸· ¶µ ¯ ¹- ºz B, » ¼¸ u ½¾ ¿&lt; Àµ My B,¥Á 1 8»·Âwµ d »·u ¥Á¹- ,7ÃÄn Ç ÅÆ (È É B, Åu ? µ 1 Ê B, Ë%u ÌÍ Î- ³n Ïn ·Ðu JÑ É ÒL ³JÑ» ·ÐJÑu ÈL...",
              "url": "https://kasfo.or.kr/SGBoard/download.asp?no=7772&bno=1740&mcbid=NOTICE04",
              "date_time": null
          }
      ]
    }

    ```
  
  + 네이버
    + request body
    ```
    {
      "keyword" : "서현",
      "searchSourceType":"NAVER",
      "sort" : "sim", // sim(정확도 순), date(날짜순)
      "start" : 1,
      "display" : 3
    }
    ```
  
    + response body
    ```
    {
      "lastBuildDate": "Wed, 22 Mar 2023 22:45:06 +0900",
      "total": 874065,
      "start": 1,
      "display": 3,
      "items": [
          {
              "title": "<b>서현</b>필라테스 리움 후기 / 성남필라테스",
              "link": "https://blog.naver.com/msny0501/223044441220",
              "description": "성남필라테스, <b>서현</b>필라테스 검색도 많이 해보고 직접 가서 상담도 받아봤었는데요 제가 최종적으로 선택해서 다니고 있는 곳은 리움 필라테스 <b>서현</b>점 이예요! 지금 생각해도 꼼꼼히 알아보고 선택하길 정말... ",
              "bloggername": "감성을 충전해드립니다",
              "bloggerlink": "blog.naver.com/msny0501",
              "postdate": "20230314"
          },
          {
              "title": "<b>서현</b> 브런치 맛집 비주얼&amp;감성 최고 카페키트",
              "link": "https://blog.naver.com/ckmiha/223050066692",
              "description": "친구들이랑 <b>서현</b>역에서 약속이 있어 어디를 갈까 알아보던 중 가성비 좋고 예쁜 <b>서현</b> 브런치 맛집을 알게 되었어요. <b>서현</b>역에서 가까운곳에 새로 오픈한 카페 키트는 요즘 제 또래 여성분들 사이에서 맛 좋고... ",
              "bloggername": "내가 사는 세상",
              "bloggerlink": "blog.naver.com/ckmiha",
              "postdate": "20230320"
          },
          {
              "title": "분당 <b>서현</b>역 맛집 고급졌던 일본요리 모슬포",
              "link": "https://blog.naver.com/lina0613/223052514537",
              "description": "지난 주말 친구들과 약속이 있어 지인에게 추천받은 분당 <b>서현</b>역 맛집에 방문했어요. 신선하고 퀄리티 좋은 일식 요리에 홀딱 반하고와 소개해 드릴게요. 그렇게 도착한 일본요리 모슬포는 <b>서현</b>저수지에서 차로... ",
              "bloggername": "바이 하르르♥ (엄마가만드는예쁜아이옷하르르공방)",
              "bloggerlink": "blog.naver.com/lina0613",
              "postdate": "20230322"
          }
      ]
    }
    ```
    
### 인기 검색어 조회 API
+ api desc :  인기 검색어 조회 기능을 제공합니다. Url 패스에 원하는 범위의 숫자를 입력하면 조회가 됩니다. 만약 탑 10을 조회 하고싶으면 10을 입력하면 됩니다.
+requset url : http://127.0.0.1:17001/search/top/{num}
+ method : GET
+ response body
	```
	[
	    {	
		"keyword": "한국",
		"searchCnt": 7
	    },
	    {
		"keyword": "네이버",
		"searchCnt": 5
	    },
	    {
		"keyword": "서현",
		"searchCnt": 4
	    },
	    {
		"keyword": "카카오",
		"searchCnt": 3
	    },
	    {
		"keyword": "판교",
		"searchCnt": 2
	    },
	    {
		"keyword": "분당",
		"searchCnt": 1
	    }
	]

## 프로젝트 주요 포인트
+  우선 어떤 API 든 쉽게 연동 가능하도록 구조화 시켜 개발하였습니다.
+ 동시성에 대한 이슈를 해결하기 위해 직접 자기자신의 데이터에 업데이트 하는 방향으로 설계 및 개발 하였습니다.
