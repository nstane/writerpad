package com.xebia.writerpad.controller;

import com.xebia.writerpad.model.ArticleReadingTime;
import com.xebia.writerpad.model.ArticleRequest;
import com.xebia.writerpad.model.ArticleResults;
import com.xebia.writerpad.model.TagMetrics;
import com.xebia.writerpad.entity.Article;
import com.xebia.writerpad.helper.DuplicateArticleContentCheckHelper;
import com.xebia.writerpad.helper.TimeToReadHelper;
import com.xebia.writerpad.service.ArticleService;
import com.xebia.writerpad.util.ArticlePageUtils;
import com.xebia.writerpad.util.ArticleUtils;
import com.xebia.writerpad.validator.ArticleValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xebia.writerpad.util.CommonUtils.isNotBlank;

@Slf4j
@RestController
@RequestMapping("/api")
public class ArticleController
{
    private final ArticleValidator validator;
    private final ArticleService articleService;
    private final TimeToReadHelper timeToReadHelper;
    private final DuplicateArticleContentCheckHelper duplicateContentCheckHelper;

    @Autowired
    public ArticleController(
            ArticleValidator validator,
            ArticleService articleService,
            TimeToReadHelper timeToReadHelper,
            DuplicateArticleContentCheckHelper duplicateContentCheckHelper
    ) {
        this.validator = validator;
        this.articleService = articleService;
        this.timeToReadHelper = timeToReadHelper;
        this.duplicateContentCheckHelper = duplicateContentCheckHelper;

    }

    @GetMapping("/articles")
    @ApiOperation(value = "Find all articles with paginated response")
    public ArticleResults findAll(
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("sortBy") String sortBy
    ) {
        return articleService.findAll(ArticlePageUtils.buildPageableRequest(pageNo, pageSize, sortBy));
    }

    @GetMapping("/articles/{slug-uuid}")
    @ApiOperation(value = "Find article by slug-uuid")
    public Article find(@PathVariable("slug-uuid") String id)
    {
        return articleService.findById(id);
    }

    @GetMapping("/articles/{slug-uuid}/timetoread")
    @ApiOperation(value = "Find time to read for a given slug-uuid")
    public ArticleReadingTime timeToRead(@PathVariable("slug-uuid") String id)
    {
        Article articleBody = articleService.findArticleIdAndBodyById(id);

        return timeToReadHelper.getTimeToRead(articleBody);
    }

    @GetMapping("/articles/tags")
    @ApiOperation(value = "Find all tags and their occurrence")
    public List<TagMetrics> tagMetrics()
    {
        return articleService.tagMetrics();
    }

    @PostMapping("/articles")
    @ApiOperation(value = "Add an article")
    public ResponseEntity addArticle(@RequestBody ArticleRequest request)
    {
        validator.validate(request);
        duplicateContentCheckHelper.checkAndValidateArticleContent(request);

        Article article = ArticleUtils.buildArticle(request);

        return new ResponseEntity(articleService.save(article), HttpStatus.CREATED);
    }

    @PatchMapping("/articles/{slug-uuid}")
    @ApiOperation(value = "Update an article")
    public ResponseEntity<Article> update(
            @RequestBody ArticleRequest request,
            @PathVariable("slug-uuid") String id
    ) {
        Article article = find(id);

        if (isNotBlank(request.getBody()))
        {
            duplicateContentCheckHelper.checkAndValidateArticleContent(request);
        }

        if (ArticleUtils.isArticleUpdatable(request, article))
        {
            articleService.save(article);
        } else {
            log.info("Request body is empty for updating id : ", id);
            throw new IllegalArgumentException("Request body is empty");
        }

        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/articles/{slug-uuid}")
    @ApiOperation(value = "Delete an article by given slug-uuid")
    public ResponseEntity delete(@PathVariable("slug-uuid") String id)
    {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
