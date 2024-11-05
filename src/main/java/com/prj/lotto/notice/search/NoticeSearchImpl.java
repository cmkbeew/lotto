package com.prj.lotto.notice.search;

import com.prj.lotto.notice.Notice;
import com.prj.lotto.notice.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class NoticeSearchImpl extends QuerydslRepositorySupport implements NoticeSearch {

    public NoticeSearchImpl() {
        super(Notice.class);
    }

    @Override
    public Page<Notice> searchAll(String[] types, String keyword, Pageable pageable) {
        QNotice notice = QNotice.notice;

        JPQLQuery<Notice> query = from(notice);

        if( (types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(notice.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(notice.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(notice.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(notice.nno.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Notice> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
