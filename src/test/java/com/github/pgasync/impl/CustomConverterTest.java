package com.github.pgasync.impl;

import com.github.pgasync.Converter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

/**
 * @author Antti Laisi
 */
public class CustomConverterTest {

    static class Json {
        final String json;
        Json(String json) {
            this.json = json;
        }
    }
    static class JsonConverter implements Converter<Json> {
        @Override
        public Class<Json> type() {
            return Json.class;
        }
        @Override
        public byte[] from(Json o) {
            return o.json.getBytes(UTF_8);
        }
        @Override
        public Json to(Oid oid, byte[] value) {
            return new Json(new String(value, UTF_8));
        }
    }

    @ClassRule
    public static DatabaseRule dbr = new DatabaseRule(DatabaseRule
            .createPoolBuilder(1)
            .converters(new JsonConverter()));

    @BeforeClass
    public static void create() {
        drop();
        dbr.query("CREATE TABLE CC_TEST (ID BIGINT, JS JSON)");
    }

    @AfterClass
    public static void drop() {
        dbr.query("DROP TABLE IF EXISTS CC_TEST");
    }

    @Test
    public void shouldConvertColumnDataToType() {
        dbr.query("INSERT INTO CC_TEST VALUES (1, $1)", singletonList("{\"a\": 1}"));
        assertEquals("{\"a\": 1}", dbr.query("SELECT * FROM CC_TEST WHERE ID = 1").row(0).get("js", Json.class).json);
    }

    @Test
    public void shouldConvertParameter() {
        dbr.query("INSERT INTO CC_TEST VALUES (2, $1)", singletonList(new Json("{\"b\": 2}")));
        assertEquals("{\"b\": 2}", dbr.query("SELECT * FROM CC_TEST WHERE ID = 2").row(0).get("js", Json.class).json);
    }

}
