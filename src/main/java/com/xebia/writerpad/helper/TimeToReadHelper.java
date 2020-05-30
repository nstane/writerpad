package com.xebia.writerpad.helper;

import com.xebia.writerpad.model.ArticleReadingTime;
import com.xebia.writerpad.model.ReadTimeDetail;
import com.xebia.writerpad.entity.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.xebia.writerpad.constant.Constants.*;

@Component
public class TimeToReadHelper
{
    @Value("${article.human_average_read_time}")
    private int averageHumanReadTime;

    public ArticleReadingTime getTimeToRead(final Article articleBody)
    {
        int wordsCount = getWordsCount(articleBody);

        return getReadingTime(articleBody, wordsCount);
    }

    private int getWordsCount(Article articleBody)
    {
        return articleBody.getBody().replaceAll(SPECIAL_CHAR_EXPRESSION, SINGLE_SPACE)
                .split(WHITE_SPACE_EXPRESSION).length;
    }

    private ArticleReadingTime getReadingTime(Article articleBody, int wordsCount)
    {
        int numberOfSecondsToReadArticle = getReadTimeInSeconds(wordsCount);

        int minutes = (numberOfSecondsToReadArticle % ONE_HOUR) / ONE_MINUTE_IN_SECONDS;
        int seconds = numberOfSecondsToReadArticle % ONE_MINUTE_IN_SECONDS;

        return new ArticleReadingTime(
                articleBody.getId(),
                new ReadTimeDetail(minutes, seconds)
        );
    }

    private int getReadTimeInSeconds(int wordsCount)
    {
        return (int) Math.ceil(
                (wordsCount * ONE_MINUTE_IN_SECONDS) / averageHumanReadTime
        );
    }
}
