package id.nerdstudio.moviecatalogue.util

import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsTest {

    @Test
    fun parseDate() {
        assertEquals("2014-10-07".parseDate(), DateTime("2014-10-07"))
        assertEquals("2012-10-10".parseDate(), DateTime("2012-10-10"))
        assertEquals("December 27, 2018".parseDate(), DateTime("2018-12-27"))
        assertEquals("December 6, 2018".parseDate(), DateTime("2018-12-06"))
        assertEquals("January 25, 2019".parseDate(), DateTime("2019-01-25"))
    }

    @Test
    fun toFormattedDate() {
        assertEquals("2014-10-07".toFormattedDate(), "Tue, 07 Oct 2014")
        assertEquals("2012-10-10".toFormattedDate(), "Wed, 10 Oct 2012")
        assertEquals("December 27, 2018".toFormattedDate(), "Thu, 27 Dec 2018")
        assertEquals("December 6, 2018".toFormattedDate(), "Thu, 06 Dec 2018")
        assertEquals("January 25, 2019".toFormattedDate(), "Fri, 25 Jan 2019")
    }
}