package com.cast;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CastTest {

    @Test
    public void testToChar() {
        assertEquals(' ', (char) Cast.toChar("", ' '));
        assertEquals(' ', (char) Cast.toChar("abc", ' '));
        assertEquals(' ', (char) Cast.toChar(null, ' '));
        assertEquals(1, (char) Cast.toChar(1, ' '));
        assertEquals('a', (char) Cast.toChar('a', ' '));
        assertEquals(' ', (char) Cast.toChar(new Date(), ' '));
    }

    @Test
    public void testToStr() {
        assertEquals("", Cast.toStr(null, ""));
        assertEquals("test", Cast.toStr("test", ""));
        assertEquals("1,2,3", Cast.toStr(new int[]{1, 2, 3}, ""));
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        assertEquals("a,b,c", Cast.toStr(list, ""));

        assertEquals("VALUE1", Cast.toStr(TestEnum.VALUE1, ""));
    }

    @Test
    public void testToBool() {
        assertTrue(Cast.toBool(null, true));
        assertTrue(Cast.toBool("", true));
        assertTrue(Cast.toBool(true, false));
        assertTrue(Cast.toBool("true", false));
        assertTrue(Cast.toBool("on", false));
        assertTrue(Cast.toBool("yes", false));
        assertTrue(Cast.toBool("1", false));

        assertFalse(Cast.toBool("false", true));
        assertFalse(Cast.toBool("off", true));
        assertFalse(Cast.toBool("no", true));
        assertFalse(Cast.toBool("0", true));
    }

    @Test
    public void testToByte() {
        assertEquals(1, (byte) Cast.toByte(null, (byte) 1));
        assertEquals(1, (byte) Cast.toByte("1", (byte) 0));
        assertEquals(0, (byte) Cast.toByte(TestEnum.VALUE1, (byte) 1));
    }

    @Test
    public void testToBytes() {
        assertArrayEquals(new byte[0], Cast.toBytes(null, new byte[0]));
        assertArrayEquals(new byte[]{97, 98, 99}, Cast.toBytes("abc", new byte[0]));
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{97, 98, 99});
        assertArrayEquals(new byte[]{97, 98, 99}, Cast.toBytes(bais, new byte[0]));
    }

    @Test
    public void testToDate() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertEquals(now, Cast.toDate(null, now));
        assertEquals(now, Cast.toDate("", now));
        assertEquals(now, Cast.toDate(now, (Date) null));
        assertEquals(calendar.getTime(), Cast.toDate("20190601", "yyyyMMdd", null));
        assertEquals(calendar.getTime(), Cast.toDate(calendar, (Date) null));
    }

    @Test
    public void testToEnum() {
        assertEquals(TestEnum.VALUE1, Cast.toEnum(null, TestEnum.class, TestEnum.VALUE1));
        assertEquals(TestEnum.VALUE1, Cast.toEnum("VALUE1", TestEnum.class, null));
        assertEquals(TestEnum.VALUE2, Cast.toEnum(TestEnum.VALUE2, TestEnum.class, null));
    }

    @Test
    public void testToArray() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        assertArrayEquals(new String[]{"A", "B", "C"}, Cast.toArray(list, String.class, null));
    }

    @Test
    public void testToCharset() {
        assertEquals(StandardCharsets.ISO_8859_1, Cast.toCharset(null, StandardCharsets.ISO_8859_1));
        assertEquals(StandardCharsets.UTF_8, Cast.toCharset("UTF-8", null));
        assertNull(Cast.toCharset("abc", null));
    }

    @Test
    public void testTo() {
        assertNull(Cast.to("", Character.class));
        assertNull(Cast.to("abc", Character.class));
        assertNull(Cast.to(null, Character.class));
        assertEquals(1, (char) Cast.to(1, Character.class));
        assertEquals('a', (char) Cast.to('a', Character.class));
        assertNull(Cast.to(new Date(), Character.class));

        assertNull(Cast.to(null, String.class));
        assertEquals("test", Cast.to("test", String.class));
        assertEquals("1,2,3", Cast.to(new int[]{1, 2, 3}, String.class));
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        assertEquals("a,b,c", Cast.to(list, String.class));
        assertEquals("VALUE1", Cast.to(TestEnum.VALUE1, String.class));

        list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        assertArrayEquals(new String[]{"A", "B", "C"}, Cast.to(list, String[].class));
    }

}
