package id.nerdstudio.moviecatalogue.util

import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtTest {

    @Test
    fun parseDate() {
        assertEquals("2014-10-07".parseDate(), DateTime("2014-10-07"))
        assertEquals("2012-10-10".parseDate(), DateTime("2012-10-10"))
        assertEquals("October 3, 2018".parseDate(), DateTime("2018-10-03"))
        assertEquals("December 6, 2018".parseDate(), DateTime("2018-12-06"))
        assertEquals("January 25, 2019".parseDate(), DateTime("2019-01-25"))
    }

    @Test
    fun toFormattedDayDate() {
        assertEquals("2014-10-07".toFormattedDayDate(), "Tue, 07 Oct 2014")
        assertEquals("2012-10-10".toFormattedDayDate(), "Wed, 10 Oct 2012")
        assertEquals("December 27, 2018".toFormattedDayDate(), "Thu, 27 Dec 2018")
        assertEquals("December 6, 2018".toFormattedDayDate(), "Thu, 06 Dec 2018")
        assertEquals("January 25, 2019".toFormattedDayDate(), "Fri, 25 Jan 2019")
    }

    @Test
    fun toFormattedDate() {
        assertEquals("2014-10-07".toFormattedDate(), "October, 07 2014")
        assertEquals("December 6, 2018".toFormattedDate(), "December, 06 2018")
        assertEquals("January 25, 2019".toFormattedDate(), "January, 25 2019")
    }
}