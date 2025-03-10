package com.bladestepapp.lifexpactivityservicemain.support;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class MongoCleanupExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        MongoTemplate mongoTemplate = SpringExtension.getApplicationContext(context)
                .getBean(MongoTemplate.class);

        mongoTemplate.getDb().drop();
    }
}
