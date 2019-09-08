package com.workspan.entity.cloner;

import com.workspan.entity.cloner.config.ApplicationConfig;
import com.workspan.entity.cloner.exception.EntityFormatException;
import com.workspan.entity.cloner.exception.EntityNotFoundException;
import com.workspan.entity.cloner.model.EntityStream;
import com.workspan.entity.cloner.service.CloneService;
import com.workspan.entity.cloner.service.Deserializer;
import com.workspan.entity.cloner.service.Serializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Client class to clone the entityId from the given entity stream file
 */
public class EntityCloner {
    public static void main(String[] args) throws EntityFormatException, EntityNotFoundException {
        if (args.length < 2) {
            System.out.println("Usage: <program_name> <inputfile> <entityid>");
            System.out.println("Example: java EntityCloner src/test/resources/input.json 5");
            return;
        }
        Integer entityId = Integer.parseInt(args[1]);
        String file = args[0];
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Deserializer deserializer = ctx.getBean(Deserializer.class);
        EntityStream entityStream = deserializer.deserialize(file, EntityStream.class);

        CloneService cloneService = ctx.getBean(CloneService.class);
        entityStream = cloneService.clone(entityStream, entityId);

        Serializer serializer = ctx.getBean(Serializer.class);
        System.out.println(serializer.serialize(entityStream));
    }
}
