package com.umc.i.src.search;

import com.umc.i.config.Constant;
import com.umc.i.src.feeds.model.get.GetAllFeedsRes;
import com.umc.i.src.market.feed.model.GetMarketFeedRes;
import com.umc.i.src.member.model.Member;
import com.umc.i.src.review.model.get.GetAllReviewsRes;
import com.umc.i.src.search.model.Keyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SearchDao implements SearchRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<GetMarketFeedRes> searchAllMarketFeedByKeywordInLatest(int userIdx,
                                                                       String search_keyword,
                                                                       int page) {
        String query = "select a.*, b.market_like_count\n" +
                "from (\n" +
                "\tselect m.*, if(ml.mem_idx22 is null, false, true) as mem_liked\n" +
                "    from (\n" +
                "\t\tselect \n" +
                "\t\t\tm.market_idx,\n" +
                "\t\t\tm.mem_idx,\n" +
                "\t\t\tm.market_group, \n" +
                "\t\t\tm.market_price, \n" +
                "\t\t\tm.market_title, \n" +
                "\t\t\tm.market_image, \n" +
                "\t\t\tm.market_soldout,\n" +
                "\t\t\tm.market_hit,\n" +
                "\t\t\tm.market_created_at\n" +
                "\t\tfrom Market m \n" +
                "        where market_title like \"%" + search_keyword +"%\" \n" +
                "        order by market_created_at DESC\n" +
                "        limit ?, ? ) m\n" +
                "\tleft join (\n" +
                "\t\tselect market_idx, mem_idx22\n" +
                "        from Market_like\n" +
                "        where mem_idx22 = ?\n" +
                "\t) ml\n" +
                "\ton m.market_idx = ml.market_idx\n" +
                ") a\n" +
                "left join (\n" +
                "\tselect market_idx, count(*) as market_like_count\n" +
                "\tfrom Market_like\n" +
                "\tgroup by market_idx\n" +
                ") b\n" +
                "on a.market_idx = b.market_idx;";

        try {
            return jdbcTemplate.query(query,
                    marketFeedByCategoryRowMapper(),
                    page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE, userIdx);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<GetMarketFeedRes> searchCategoryMarketFeedByKeywordInLatest(int userIdx,
                                                                            String categoryIdx,
                                                                            String search_keyword,
                                                                            int page) {
        String query = "select a.*, b.market_like_count\n" +
                "from (\n" +
                "\tselect m.*, if(ml.mem_idx22 is null, false, true) as mem_liked\n" +
                "    from (\n" +
                "\t\tselect \n" +
                "\t\t\tm.market_idx,\n" +
                "\t\t\tm.mem_idx,\n" +
                "\t\t\tm.market_group, \n" +
                "\t\t\tm.market_price, \n" +
                "\t\t\tm.market_title, \n" +
                "\t\t\tm.market_image, \n" +
                "\t\t\tm.market_soldout,\n" +
                "\t\t\tm.market_hit,\n" +
                "\t\t\tm.market_created_at\n" +
                "\t\tfrom Market m \n" +
                "        where market_title like \"%" + search_keyword + "%\" and market_group = ?\n" +
                "        order by market_created_at DESC\n" +
                "        limit ?, ? ) m\n" +
                "\tleft join (\n" +
                "\t\tselect market_idx, mem_idx22\n" +
                "        from Market_like\n" +
                "        where mem_idx22 = ?\n" +
                "\t) ml\n" +
                "\ton m.market_idx = ml.market_idx\n" +
                ") a\n" +
                "left join (\n" +
                "\tselect market_idx, count(*) as market_like_count\n" +
                "\tfrom Market_like\n" +
                "\tgroup by market_idx\n" +
                ") b\n" +
                "on a.market_idx = b.market_idx;";

        try {
            return jdbcTemplate.query(query, marketFeedByCategoryRowMapper(),
                    categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE, userIdx);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<GetMarketFeedRes> searchAllMarketFeedByKeywordByTitleContentInLatest(int userIdx,
                                                                                     String search_keyword,
                                                                                     int page) {
        String query = "select a.*, b.market_like_count\n" +
                "from (\n" +
                "\tselect m.*, if(ml.mem_idx22 is null, false, true) as mem_liked\n" +
                "    from (\n" +
                "\t\tselect \n" +
                "\t\t\tm.market_idx,\n" +
                "\t\t\tm.mem_idx,\n" +
                "\t\t\tm.market_group, \n" +
                "\t\t\tm.market_price, \n" +
                "\t\t\tm.market_title, \n" +
                "\t\t\tm.market_image, \n" +
                "\t\t\tm.market_soldout,\n" +
                "\t\t\tm.market_hit,\n" +
                "\t\t\tm.market_created_at\n" +
                "\t\tfrom Market m \n" +
                "        where market_title like \"%" + search_keyword + "%\" or market_content like \"%" + search_keyword + "%\" \n" +
                "        order by market_created_at DESC\n" +
                "        limit ?, ? ) m\n" +
                "\tleft join (\n" +
                "\t\tselect market_idx, mem_idx22\n" +
                "        from Market_like\n" +
                "        where mem_idx22 = ?\n" +
                "\t) ml\n" +
                "\ton m.market_idx = ml.market_idx\n" +
                ") a\n" +
                "left join (\n" +
                "\tselect market_idx, count(*) as market_like_count\n" +
                "\tfrom Market_like\n" +
                "\tgroup by market_idx\n" +
                ") b\n" +
                "on a.market_idx = b.market_idx;";

        try {
            return jdbcTemplate.query(query, marketFeedByCategoryRowMapper(),
                    page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE, userIdx);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<GetMarketFeedRes> searchCategoryMarketFeedByKeywordByTitleContentInLatest(int userIdx,
                                                                                          String categoryIdx,
                                                                                          String search_keyword,
                                                                                          int page) {
        String query = "select a.*, b.market_like_count\n" +
                "from (\n" +
                "\tselect m.*, if(ml.mem_idx22 is null, false, true) as mem_liked\n" +
                "    from (\n" +
                "\t\tselect \n" +
                "\t\t\tm.market_idx,\n" +
                "\t\t\tm.mem_idx,\n" +
                "\t\t\tm.market_group, \n" +
                "\t\t\tm.market_price, \n" +
                "\t\t\tm.market_title, \n" +
                "\t\t\tm.market_image, \n" +
                "\t\t\tm.market_soldout,\n" +
                "\t\t\tm.market_hit,\n" +
                "\t\t\tm.market_created_at\n" +
                "\t\tfrom Market m \n" +
                "        where (market_title like \"%" + search_keyword + "%\" or market_content like \"%" + search_keyword + "%\") and market_group = ?\n" +
                "        order by market_created_at DESC\n" +
                "        limit ?, ? ) m\n" +
                "\tleft join (\n" +
                "\t\tselect market_idx, mem_idx22\n" +
                "        from Market_like\n" +
                "        where mem_idx22 = ?\n" +
                "\t) ml\n" +
                "\ton m.market_idx = ml.market_idx\n" +
                ") a\n" +
                "left join (\n" +
                "\tselect market_idx, count(*) as market_like_count\n" +
                "\tfrom Market_like\n" +
                "\tgroup by market_idx\n" +
                ") b\n" +
                "on a.market_idx = b.market_idx;";

        try {
            return jdbcTemplate.query(query, marketFeedByCategoryRowMapper(),
                    categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE, userIdx);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<GetMarketFeedRes> searchAllMarketFeedByKeywordByNicknameInLatest(int userIdx,
                                                                                 String search_keyword,
                                                                                 int page) {
        List<Integer> searchMemberIdx = findMemberNickNameByMemberIdx(search_keyword);
        String memberIdxList = null;
        if (searchMemberIdx == null) {
            return null;
        } else {
            memberIdxList = makeMemberIdxString(searchMemberIdx);
        }

        String query = "select a.*, b.market_like_count\n" +
                "from (\n" +
                "\tselect m.*, if(ml.mem_idx22 is null, false, true) as mem_liked\n" +
                "    from (\n" +
                "\t\tselect \n" +
                "\t\t\tm.market_idx,\n" +
                "\t\t\tm.mem_idx,\n" +
                "\t\t\tm.market_group, \n" +
                "\t\t\tm.market_price, \n" +
                "\t\t\tm.market_title, \n" +
                "\t\t\tm.market_image, \n" +
                "\t\t\tm.market_soldout,\n" +
                "\t\t\tm.market_hit,\n" +
                "\t\t\tm.market_created_at\n" +
                "\t\tfrom Market m \n" +
                "        where mem_idx in" + memberIdxList + " \n" +
                "        order by market_created_at DESC\n" +
                "        limit ?, ? ) m\n" +
                "\tleft join (\n" +
                "\t\tselect market_idx, mem_idx22\n" +
                "        from Market_like\n" +
                "        where mem_idx22 = ?\n" +
                "\t) ml\n" +
                "\ton m.market_idx = ml.market_idx\n" +
                ") a\n" +
                "left join (\n" +
                "\tselect market_idx, count(*) as market_like_count\n" +
                "\tfrom Market_like\n" +
                "\tgroup by market_idx\n" +
                ") b\n" +
                "on a.market_idx = b.market_idx;";

        try {
            log.info("{}", query);
            return jdbcTemplate.query(query, marketFeedByCategoryRowMapper(),
                    page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE, userIdx);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<GetMarketFeedRes> searchCategoryMarketFeedByKeywordByNicknameInLatest(int userIdx,
                                                                                      String categoryIdx,
                                                                                      String search_keyword,
                                                                                      int page) {
        List<Integer> searchMemberIdx = findMemberNickNameByMemberIdx(search_keyword);

        String memberIdxList = null;
        if (searchMemberIdx == null) {
            return null;
        } else {
            memberIdxList = makeMemberIdxString(searchMemberIdx);
        }

        String query = "select a.*, b.market_like_count\n" +
                "from (\n" +
                "\tselect m.*, if(ml.mem_idx22 is null, false, true) as mem_liked\n" +
                "    from (\n" +
                "\t\tselect \n" +
                "\t\t\tm.market_idx,\n" +
                "\t\t\tm.mem_idx,\n" +
                "\t\t\tm.market_group, \n" +
                "\t\t\tm.market_price, \n" +
                "\t\t\tm.market_title, \n" +
                "\t\t\tm.market_image, \n" +
                "\t\t\tm.market_soldout,\n" +
                "\t\t\tm.market_hit,\n" +
                "\t\t\tm.market_created_at\n" +
                "\t\tfrom Market m \n" +
                "        where mem_idx in" + memberIdxList + " and market_group = ?\n" +
                "        order by market_created_at DESC\n" +
                "        limit ?, ? ) m\n" +
                "\tleft join (\n" +
                "\t\tselect market_idx, mem_idx22\n" +
                "        from Market_like\n" +
                "        where mem_idx22 = ?\n" +
                "\t) ml\n" +
                "\ton m.market_idx = ml.market_idx\n" +
                ") a\n" +
                "left join (\n" +
                "\tselect market_idx, count(*) as market_like_count\n" +
                "\tfrom Market_like\n" +
                "\tgroup by market_idx\n" +
                ") b\n" +
                "on a.market_idx = b.market_idx;";

        try {
            return jdbcTemplate.query(query, marketFeedByCategoryRowMapper(),
                    categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE, userIdx);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllReviewsRes> searchAllReviewFeedByKeywordByContentInLatest(String search_keyword, int page) {
        String query = "select review_idx, sell_mem_idx, A.mem_nickname as seller_nick, buy_mem_idx, B.mem_nickname as buyer_nick, B.mem_profile_url \n" +
                "I.Market_review.review_goods, review_content, review_hit, review_created_at, review_image \n" +
                "from Market_review, Member A, Member B\n" +
                "where Market_review.sell_mem_idx = A.mem_idx && Market_review.buy_mem_idx = B.mem_idx and review_content like \"%" + search_keyword + "%\"\n" +
                "order by review_created_at desc limit ?, ?;";

        try {
            return jdbcTemplate.query(query,
                    (rs, rowNum) -> new GetAllReviewsRes(
                            rs.getInt("review_idx"),
                            rs.getInt("buy_mem_idx"),
                            rs.getInt("sell_mem_idx"),
                            rs.getString("buyer_nick"),
                            rs.getString("seller_nick"),
                            rs.getString("mem_profile_url"),
                            rs.getString("review_goods"),
                            rs.getInt("review_hit"),
                            0, 0,
                            rs.getString("review_created_at"),
                            rs.getString("review_image")),
                    page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllDairyFeedByKeywordByTitleInLatest(String search_keyword, int page) {
        String query = "select D.diary_idx, diary_roomType, D.mem_idx, M.mem_nickname, diary_title, diary_image, diary_hit, diary_created_at,\n" +
                "if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Diary_feed D, Member M, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt\n" +
                "where diary_blame < 10 && D.mem_idx = M.mem_idx and diary_title like \"%" + search_keyword + "%\" group by diary_idx order by diary_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, diaryFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchCategoryDairyFeedByKeywordByTitleInLatest(String categoryIdx, String search_keyword, int page) {
        String query = "select D.diary_idx, diary_roomType, D.mem_idx, M.mem_nickname, diary_title, diary_image, diary_hit, diary_created_at,\n" +
                "if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Diary_feed D, Member M, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt \n" +
                "where diary_blame < 10 && D.mem_idx = M.mem_idx and diary_roomType = ? and diary_title like \"%" + search_keyword + "%\" \n" +
                "group by diary_idx order by diary_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, diaryFeedRowMapper(), categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllDairyFeedByKeywordByTitleContentInLatest(String search_keyword, int page) {
        String query = "select D.diary_idx, diary_roomType, D.mem_idx, M.mem_nickname, diary_title, diary_image, diary_hit, diary_created_at,\n" +
                "if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Diary_feed D, Member M, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt \n" +
                "where diary_blame < 10 && D.mem_idx = M.mem_idx and (diary_title like \"%"+ search_keyword +"%\" or diary_content like \"%" + search_keyword +"%\") \n" +
                "group by diary_idx order by diary_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, diaryFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchCategoryDairyFeedByKeywordByTitleContentInLatest(String categoryIdx, String search_keyword, int page) {
        String query = "select D.diary_idx, diary_roomType, D.mem_idx, M.mem_nickname, diary_title, diary_image, diary_hit, diary_created_at,\n" +
                "if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Diary_feed D, Member M, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt \n" +
                "where diary_blame < 10 && D.mem_idx = M.mem_idx and diary_roomType = ? and (diary_title like \"%" + search_keyword + "%\" or diary_content like \"%" + search_keyword + "%\") \n" +
                "group by diary_idx order by diary_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, diaryFeedRowMapper(), categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllDairyFeedByKeywordByMemberNicknameInLatest(String search_keyword, int page) {
        String query = "select * \n" +
                "from (\n" +
                "select D.diary_idx, diary_roomType, D.mem_idx, M.mem_nickname, diary_title, diary_image, diary_hit, diary_created_at, \n" +
                "if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Diary_feed D, Member M, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt\n" +
                "where diary_blame < 10 && D.mem_idx = M.mem_idx group by diary_idx order by diary_idx desc limit ?, ?\n" +
                ") a\n" +
                "where mem_nickname like \"%" + search_keyword + "%\";";
        try {
            return jdbcTemplate.query(query, diaryFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchCategoryDairyFeedByKeywordByMemberNicknameInLatest(String categoryIdx, String search_keyword, int page) {
        String query = "select * \n" +
                "from (\n" +
                "select D.diary_idx, diary_roomType, D.mem_idx, M.mem_nickname, diary_title, diary_image, diary_hit, diary_created_at, \n" +
                "if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Diary_feed D, Member M, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt\n" +
                "where diary_roomType = ? and diary_blame < 10 && D.mem_idx = M.mem_idx group by diary_idx order by diary_idx desc limit ?, ? \n" +
                ") a\n" +
                "where mem_nickname like \"%" + search_keyword + "%\";";
        try {
            return jdbcTemplate.query(query, diaryFeedRowMapper(), categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllStoryFeedByKeywordByTitleInLatest(String search_keyword, int page) {
        String query = "select S.story_idx, story_roomType, S.mem_idx, mem_nickname, story_title, story_image, story_hit, story_created_at, \n" +
                "if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && story_blame < 10 and story_title like \"%" + search_keyword + "%\"  group by story_idx order by story_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, storyFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchCategoryStoryFeedByKeywordByTitleInLatest(String categoryIdx, String search_keyword, int page) {
        String query = "select S.story_idx, story_roomType, S.mem_idx, mem_nickname, story_title, story_image, story_hit, story_created_at, \n" +
                "if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && story_blame < 10 and story_roomType = ? and story_title like \"%" + search_keyword + "%\"  group by story_idx order by story_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, storyFeedRowMapper(), categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllStoryFeedByKeywordByTitleContentInLatest(String search_keyword, int page) {
        String query = "select S.story_idx, story_roomType, S.mem_idx, mem_nickname, story_title, story_image, story_hit, story_created_at, \n" +
                "if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && story_blame < 10 and (story_title like \"%" + search_keyword + "%\" or story_content like \"%" + search_keyword + "%\") \n" +
                "group by story_idx order by story_idx desc limit ?, ?;";
        try {
            log.info("{}", query);
            return jdbcTemplate.query(query, storyFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchCategoryStoryFeedByKeywordByTitleContentInLatest(String categoryIdx, String search_keyword, int page) {
        String query = "select S.story_idx, story_roomType, S.mem_idx, mem_nickname, story_title, story_image, story_hit, story_created_at, \n" +
                "if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && story_blame < 10 and story_roomType = ? and (story_title like \"%" + search_keyword + "%\" or story_content like \"%" + search_keyword + "%\")  \n" +
                "group by story_idx order by story_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, storyFeedRowMapper(), categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllStoryFeedByKeywordByMemberNicknameInLatest(String search_keyword, int page) {
        String query = "select S.story_idx, story_roomType, S.mem_idx, mem_nickname, story_title, story_image, story_hit, story_created_at, \n" +
                "if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && story_blame < 10 and mem_nickname like \"%" + search_keyword + "%\"\n" +
                "group by story_idx order by story_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, storyFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchCategoryStoryFeedByKeywordByMemberNicknameInLatest(String categoryIdx, String search_keyword, int page) {
        String query = "select S.story_idx, story_roomType, S.mem_idx, mem_nickname, story_title, story_image, story_hit, story_created_at, \n" +
                "if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && story_blame < 10 and story_roomType = ? and mem_nickname like \"%" + search_keyword + "%\"\n" +
                "group by story_idx order by story_idx desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, storyFeedRowMapper(), categoryIdx, page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllHomeFeedByKeywordByTitleInLatest(String search_keyword, int page) {
        String query = "select *\n" +
                "from (\n" +
                "select 1 as boardType, S.story_idx as feedIdx, story_roomType as roomType, S.mem_idx, mem_nickname, story_title as title, story_image as image, story_hit as hit, story_created_at as createAt, if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && S.story_blame < 10 group by S.story_idx UNION\n" +
                "select 2 as boardType, D.diary_idx as feedIdx, diary_roomType as roomType, D.mem_idx, mem_nickname, diary_title as title, diary_image as image, diary_hit as hit, diary_created_at as createAt, if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Diary_feed D, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt\n" +
                "where D.diary_blame < 10 group by D.diary_idx UNION\n" +
                "select 3 as boardType, review_idx as feedIdx, null as roomType, buy_mem_idx as mem_idx, B.mem_nickname as mem_nickname, concat(A.mem_nickname, '님과 ', I.Market_review.review_goods, ' 을 거래했습니다.') as title, review_image as image, review_hit as hit, review_created_at as createAt, 0 as comment_cnt\n" +
                "from Market_review, Member A, Member B where Market_review.sell_mem_idx = A.mem_idx && Market_review.buy_mem_idx = B.mem_idx && Market_review.review_blame < 10 ) t\n" +
                "where title like \"%" + search_keyword + "%\" order by createAt desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, homeAllFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllHomeFeedByKeywordByTitleContentInLatest(String search_keyword, int page) {
        String query = "select t.boardType, t.feedIdx, t.roomType, t.mem_Idx, t.mem_nickname, t.title, t.image, t.hit, t.createAt, t.comment_cnt \n" +
                "from (\n" +
                "select 1 as boardType, S.story_idx as feedIdx, story_roomType as roomType, S.mem_idx, mem_nickname, story_title as title, story_content as content, story_image as image, story_hit as hit, story_created_at as createAt, if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt \n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && S.story_blame < 10 group by S.story_idx UNION\n" +
                "select 2 as boardType, D.diary_idx as feedIdx, diary_roomType as roomType, D.mem_idx, mem_nickname, diary_title as title, diary_content as content, diary_image as image, diary_hit as hit, diary_created_at as createAt, if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt \n" +
                "from Diary_feed D, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt \n" +
                "where D.diary_blame < 10 group by D.diary_idx UNION\n" +
                "select 3 as boardType, review_idx as feedIdx, null as roomType, buy_mem_idx as mem_idx, B.mem_nickname as mem_nickname, concat(A.mem_nickname, '님과 ', I.Market_review.review_goods, ' 을 거래했습니다.') as title, review_content as content, review_image as image, review_hit as hit, review_created_at as createAt, 0 as comment_cnt\n" +
                "from Market_review, Member A, Member B where Market_review.sell_mem_idx = A.mem_idx && Market_review.buy_mem_idx = B.mem_idx && Market_review.review_blame < 10 ) t\n" +
                "where title like \"%" + search_keyword + "%\" or content like \"%" + search_keyword +"%\" order by createAt desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, homeAllFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<GetAllFeedsRes> searchAllHomeFeedByKeywordByMemberNicknameInLatest(String search_keyword, int page) {
        String query = "select *\n" +
                "from (\n" +
                "select 1 as boardType, S.story_idx as feedIdx, story_roomType as roomType, S.mem_idx, mem_nickname, story_title as title, story_image as image, story_hit as hit, story_created_at as createAt, if(S.story_idx = Cmt.story_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Story_feed S, Member M, (select story_idx, count(*) as comment_cnt from Story_feed_comment group by story_idx) Cmt\n" +
                "where S.mem_idx = M.mem_idx && S.story_blame < 10 group by S.story_idx UNION\n" +
                "select 2 as boardType, D.diary_idx as feedIdx, diary_roomType as roomType, D.mem_idx, mem_nickname, diary_title as title, diary_image as image, diary_hit as hit, diary_created_at as createAt, if(D.diary_idx = Cmt.diary_idx, comment_cnt, 0) as comment_cnt\n" +
                "from Diary_feed D, (select diary_idx, count(*) as comment_cnt from Diary_comment group by diary_idx) Cmt\n" +
                "where D.diary_blame < 10 group by D.diary_idx UNION\n" +
                "select 3 as boardType, review_idx as feedIdx, null as roomType, buy_mem_idx as mem_idx, B.mem_nickname as mem_nickname, concat(A.mem_nickname, '님과 ', I.Market_review.review_goods, ' 을 거래했습니다.') as title, review_image as image, review_hit as hit, review_created_at as createAt, 0 as comment_cnt\n" +
                "from Market_review, Member A, Member B where Market_review.sell_mem_idx = A.mem_idx && Market_review.buy_mem_idx = B.mem_idx && Market_review.review_blame < 10 ) t\n" +
                "where mem_nickname like \"%" + search_keyword + "%\" order by createAt desc limit ?, ?;";
        try {
            return jdbcTemplate.query(query, homeAllFeedRowMapper(), page * Constant.FEED_PER_PAGE, Constant.FEED_PER_PAGE);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Keyword> bestSearchKeyword() {
        String query = "select keyword from Hot_keyword limit 7;";

        try {
            List<Keyword> keyword = jdbcTemplate.query(query, (rs, rowNum) -> new Keyword(
                    rs.getString("keyword")
            ));
            return keyword;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private List<Integer> findMemberNickNameByMemberIdx(String userNickname) {
        String query = "select * from Member where mem_nickname like \"%" + userNickname + "%\";";

        try {
            List<Member> result = jdbcTemplate.query(query, memberRowMapper());
            List<Integer> res = new ArrayList<>();
            for (Member member : result) {
                res.add(member.getMemIdx());
            }
            return res;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private String makeMemberIdxString(List<Integer> memberIdx) {
        String res = "(";
        if (memberIdx.size() == 0) {
            return null;
        }
        for (int i = 0; i < memberIdx.size(); i++) {
            if (i == memberIdx.size() - 1) {
                res += memberIdx.get(i) + ")";
            } else {
                res += memberIdx.get(i) + ", ";
            }
        }
        return res;
    }

    private RowMapper<Member> memberRowMapper() {
        return ((rs, rowNum) -> {
            Member member = new Member();
            member.setMemIdx(rs.getInt("mem_idx"));
            return member;
        });
    }

    private RowMapper<GetMarketFeedRes> marketFeedByCategoryRowMapper() {
        return (rs, rowNum) -> {
            GetMarketFeedRes marketFeed = new GetMarketFeedRes();
            marketFeed.setMarketIdx(rs.getInt("market_idx"));
            marketFeed.setUserIdx(rs.getInt("mem_idx"));
            marketFeed.setCategory(rs.getInt("market_group"));
            marketFeed.setTitle(rs.getString("market_title"));
            marketFeed.setPrice(rs.getInt("market_price"));
            marketFeed.setSoldout(rs.getString("market_soldout"));
            marketFeed.setImage(rs.getString("market_image"));
            marketFeed.setLikeCount(rs.getInt("market_like_count"));
            marketFeed.setHit(rs.getInt("market_hit"));
            marketFeed.setCreatedAt(rs.getTimestamp("market_created_at"));
            marketFeed.setUserLiked(rs.getBoolean("mem_liked"));
            return marketFeed;
        };
    }

    private RowMapper<GetAllFeedsRes> diaryFeedRowMapper() {
        return (rs, rowNum) -> {
            GetAllFeedsRes res = new GetAllFeedsRes();
            res.setBoardType(rs.getInt("diary_roomType"));
            res.setFeedIdx(rs.getInt("diary_idx"));
            res.setMemIdx(rs.getInt("mem_idx"));
            res.setMemNick(rs.getString("mem_nickname"));
            res.setTitle(rs.getString("diary_title"));
            res.setImg(rs.getString("diary_image"));
            res.setHit(rs.getInt("diary_hit"));
            res.setCommentCnt(rs.getInt("comment_cnt"));
            res.setCreateAt(rs.getString("diary_created_at"));
            return res;
        };
    }
    private RowMapper<GetAllFeedsRes> storyFeedRowMapper() {
        return (rs, rowNum) -> {
            GetAllFeedsRes res = new GetAllFeedsRes();
            res.setBoardType(rs.getInt("story_roomType"));
            res.setFeedIdx(rs.getInt("story_idx"));
            res.setMemIdx(rs.getInt("mem_idx"));
            res.setMemNick(rs.getString("mem_nickname"));
            res.setTitle(rs.getString("story_title"));
            res.setImg(rs.getString("story_image"));
            res.setHit(rs.getInt("story_hit"));
            res.setCommentCnt(rs.getInt("comment_cnt"));
            res.setCreateAt(rs.getString("story_created_at"));
            return res;
        };
    }

    private RowMapper<GetAllFeedsRes> homeAllFeedRowMapper() {
        return (rs, rowNum) -> {
            GetAllFeedsRes res = new GetAllFeedsRes();
            res.setBoardType(rs.getInt("boardType"));
            res.setRoomType(rs.getInt("roomType"));
            res.setFeedIdx(rs.getInt("feedIdx"));
            res.setMemIdx(rs.getInt("mem_idx"));
            res.setMemNick(rs.getString("mem_nickname"));
            res.setTitle(rs.getString("title"));
            res.setImg(rs.getString("image"));
            res.setHit(rs.getInt("hit"));
            res.setCommentCnt(rs.getInt("comment_cnt"));
            res.setCreateAt(rs.getString("createAt"));
            return res;
        };
    }
}
