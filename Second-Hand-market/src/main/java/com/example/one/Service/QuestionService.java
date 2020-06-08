package com.example.one.Service;

import com.example.one.Dto.PaginationDTO;
import com.example.one.Dto.QuestionDTO;
import com.example.one.Dto.QuestionQueryDTO;
import com.example.one.exception.CustomizeException;
import com.example.one.mapper.ExpendMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.one.exception.CustomizeErrorCode.NO_LOGIN;
import static com.example.one.exception.CustomizeErrorCode.QUESTION_NOT_FOUND;

@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private ExpendMapper expendMapper;

    public void incLikeCount(Long id) {
        Question record = new Question();
        record.setLikeCount(1);
        record.setId(id);
        expendMapper.incQuestionLikeCount(record);
    }

    public PaginationDTO List(String search, Integer page, Integer size) {
        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
            PaginationDTO paginationDTO = new PaginationDTO();
            QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
            questionQueryDTO.setSearch(search);
            Integer totalCount = expendMapper.countBySearch(questionQueryDTO);
            paginationDTO.setPagination(totalCount, page, size);
            Integer offset = size * (paginationDTO.getCurrentPage() - 1);
            QuestionExample example = new QuestionExample();
            example.setOrderByClause("gmt_create desc");
            questionQueryDTO.setSize(size);
            questionQueryDTO.setPage(offset);
            List<Question> questions = expendMapper.selectBySearch(questionQueryDTO);

            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question question : questions) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
                if (user == null) {
                    throw new CustomizeException(NO_LOGIN);
                }
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            paginationDTO.setData(questionDTOList);
            return paginationDTO;
        } else {
            PaginationDTO paginationDTO = new PaginationDTO();
            Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
            paginationDTO.setPagination(totalCount, page, size);
            Integer offset = size * (paginationDTO.getCurrentPage() - 1);
            QuestionExample example = new QuestionExample();
            example.setOrderByClause("gmt_create desc");
            List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,
                    new RowBounds(offset, size));
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question question : questions) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
                if (user == null) {
                    throw new CustomizeException(NO_LOGIN);
                }
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            paginationDTO.setData(questionDTOList);
            return paginationDTO;
        }


    }

    public PaginationDTO profileList(Long id, Integer page, Integer size) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(id);
        Integer totalCount = (int) questionMapper.countByExample(example);


//        Integer totalCount = questionMapper.profileCount(id);

        paginationDTO.setPagination(totalCount, page, size);

        //size(page-1)
        Integer offset = size * (paginationDTO.getCurrentPage() - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,
                new RowBounds(offset, size));
//        List<Question> questions = questionMapper.profileList(id,offset,size);
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            if (user == null) {
                throw new CustomizeException(NO_LOGIN);
            }
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        if (user == null) {
            throw new CustomizeException(NO_LOGIN);
        }
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void creatOrUpdate(Question question) {

        if (question.getId() == null) {
            //创建插入数据
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {
            //数据已经存在，则更新数据
            //question.setGmtModified(question.getGmtCreate());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int isUpdate = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (isUpdate != 1) {
                throw new CustomizeException(QUESTION_NOT_FOUND);
            }

        }
    }

    public void incView(Long id) {
//        Question question = questionMapper.selectByPrimaryKey(id);
//        Question UpdateQuestion = new Question();
//        UpdateQuestion.setViewCount(question.getViewCount()+1);
//        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria()
//                .andIdEqualTo(id);
//        questionMapper.updateByExampleSelective(UpdateQuestion,questionExample);
        Question record = new Question();
        record.setViewCount(1);
        record.setId(id);
        expendMapper.incViewCount(record);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }

        String[] tags = StringUtils.split(queryDTO.getTag(), " ");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = expendMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());

        return questionDTOS;
    }

    public PaginationDTO recommendList(Integer size) {

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("view_count desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,
                new RowBounds(0, size));
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            if (user == null) {
                throw new CustomizeException(NO_LOGIN);
            }
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;

    }
}


//    alter table COMMENT
//        add id long auto_increment;
//
//        alter table COMMENT
//        add parent_id long not null;
//
//        alter table COMMENT
//        add type int not null;
//
//        alter table COMMENT
//        add commentator int not null;
//
//        alter table COMMENT
//        add gmt_create bigint not null;
//
//        alter table COMMENT
//        add gmt_modified bigint not null;
//
//        alter table COMMENT
//        add like_count bigint default 0 not null;
//
//        alter table COMMENT
//        add constraint COMMENT_pk
//        primary key (id);


