package com.baeldung.lsd.persistence.model.predicates;

import java.time.LocalDate;

import com.baeldung.lsd.persistence.model.QTask;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

public final class TaskPredicates {

    private TaskPredicates() {
    }

    public static BooleanExpression tasksWithNameContaining(String nameLike) {
        return QTask.task.name.contains(nameLike);
    }

    public static BooleanExpression tasksWithStatusEquals(TaskStatus status) {
        return QTask.task.status.eq(status);
    }

    public static OrderSpecifier<LocalDate> tasksSortedByDueDateDesc() {
        return QTask.task.dueDate.desc();
    }

    public static BooleanExpression tasksByCampaignCode(String campaignCode) {
        return QTask.task.campaign.code.eq(campaignCode);
    }

}
