package com.umc.i.src.search;


import com.umc.i.src.market.feed.model.GetMarketFeedRes;

import java.util.List;

public interface SearchService {

    List<GetMarketFeedRes> searchAllMarketFeedByKeywordByTitleInLatest(int userIdx, String search_keyword, int page);

    List<GetMarketFeedRes> searchCategoryMarketFeedByKeywordByTitleInLatest(int userIdx, String categoryIdx, String search_keyword, int page);

    List<GetMarketFeedRes> searchAllMarketFeedByKeywordByTitleContentInLatest(int userIdx, String search_keyword, int page);

    List<GetMarketFeedRes> searchCategoryMarketFeedByKeywordByTitleContentInLatest(int userIdx, String categoryIdx, String search_keyword, int page);

    List<GetMarketFeedRes> searchAllMarketFeedByKeywordByNicknameInLatest(int userIdx, String search_keyword, int page);

    List<GetMarketFeedRes> searchCategoryMarketFeedByKeywordByNicknameInLatest(int userIdx, String categoryIdx, String search_keyword, int page);
}
