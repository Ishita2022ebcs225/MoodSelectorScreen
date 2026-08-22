package com.example.moodselector.presentations.reading

import com.example.moodselector.R

/*
 * ==========================================================
 * READING BOOK
 * ==========================================================
 *
 * The existing ReadingStory name is intentionally preserved
 * so the current navigation and ReadingStoryScreen continue
 * to work without requiring changes elsewhere.
 *
 * coverResId contains the local drawable resource for each
 * book cover. Covers are bundled with the app and therefore
 * do not require an Internet connection.
 */

data class ReadingStory(
    val id: String,
    val title: String,
    val subtitle: String,
    val story: String,
    val coverResId: Int? = null
)


/*
 * ==========================================================
 * RECOMMENDED MENTAL HEALTH BOOKS
 * ==========================================================
 */

object ReadingStories {

    val stories = listOf(

        /*
         * ==================================================
         * 1. FURIOUSLY HAPPY
         * ==================================================
         */

        ReadingStory(
            id = "furiously_happy",
            title = "Furiously Happy",
            subtitle = "Jenny Lawson • Mental health, humour, and resilience",
            coverResId = R.drawable.furiously_happy,
            story = """
                Furiously Happy by Jenny Lawson is a humorous and personal exploration of living with mental illness.

                Rather than presenting mental health as something that must always be discussed in serious or clinical language, Lawson uses humour and personal experiences to talk about anxiety, depression, and the challenges of everyday life.

                The book explores the importance of finding moments of joy even when life is difficult.

                Why it may be useful:

                • Reducing shame around mental health struggles
                • Finding humour during difficult moments
                • Recognizing that difficult emotions can coexist with positive experiences
                • Encouraging openness about mental health
                • Appreciating small moments of happiness
                • Challenging the idea that wellbeing must look perfect

                Its approach is different from a CBT workbook. Instead, it offers a personal perspective that can remind readers that struggling does not remove their ability to experience humour, connection, or joy.

                It is a memoir rather than a clinical treatment guide.
            """.trimIndent()
        ),


        /*
         * ==================================================
         * 2. WHY HAS NOBODY TOLD ME THIS BEFORE?
         * ==================================================
         */

        ReadingStory(
            id = "why_has_nobody_told_me",
            title = "Why Has Nobody Told Me This Before?",
            subtitle = "Dr Julie Smith • Practical mental health tools",
            coverResId = R.drawable.why_has_nobody_told_me,
            story = """
                Why Has Nobody Told Me This Before? was written by clinical psychologist Dr Julie Smith.

                The book presents practical mental health strategies in short, accessible sections, allowing readers to focus on a particular challenge when they need it.

                Topics include anxiety, low mood, self-confidence, motivation, criticism, resilience, and learning to respond more kindly to yourself.

                Why it may be useful:

                • Practical strategies for everyday challenges
                • Understanding anxiety and low mood
                • Building self-confidence
                • Finding motivation
                • Developing resilience
                • Learning to respond more kindly to yourself

                Its short sections make it suitable for reading a few pages at a time rather than working through a long chapter in one sitting.

                It is a general mental health resource and should not be considered a replacement for therapy or professional mental health support.
            """.trimIndent()
        ),


        /*
         * ==================================================
         * 3. THE ANXIOUS GENERATION
         * ==================================================
         */

        ReadingStory(
            id = "the_anxious_generation",
            title = "The Anxious Generation",
            subtitle = "Jonathan Haidt • Anxiety, wellbeing, and technology",
            coverResId = R.drawable.the_anxious_generation,
            story = """
                The Anxious Generation by social psychologist Jonathan Haidt examines the rise of anxiety and other mental health difficulties among young people.

                The book explores how changes in childhood, technology, social media, and reduced opportunities for real-world independence may affect wellbeing.

                Haidt discusses the shift from a more play-based childhood toward an increasingly phone-based environment.

                Why it may be useful:

                • Understanding factors that can affect mental health
                • Exploring the relationship between technology and wellbeing
                • Thinking about social media habits
                • Understanding the importance of real-world connection
                • Encouraging healthier technology boundaries
                • Reflecting on activities that support wellbeing

                The book can encourage readers to think critically about their relationship with technology and the environments around them.

                Its arguments represent one perspective on a complex area of mental health research and should not be treated as a complete explanation for anxiety or depression.
            """.trimIndent()
        ),


        /*
         * ==================================================
         * 4. DOING CBT
         * ==================================================
         */

        ReadingStory(
            id = "doing_cbt",
            title = "Doing CBT",
            subtitle = "David F. Tolin, PhD • Cognitive behavioural therapy",
            coverResId = R.drawable.doing_cbt,
            story = """
                Doing CBT by psychologist David F. Tolin introduces readers to the principles and practical techniques of cognitive behavioral therapy.

                CBT focuses on the relationship between thoughts, feelings, and behaviors and explores how changing unhelpful patterns can influence emotional wellbeing.

                The book presents CBT as something that involves actively practising skills rather than simply learning about psychological concepts.

                Why it may be useful:

                • Understanding the basic CBT model
                • Identifying unhelpful thinking patterns
                • Examining connections between thoughts and emotions
                • Changing behaviors that maintain difficulties
                • Practising structured coping strategies
                • Developing more balanced ways of responding

                This book is particularly relevant to readers who want to understand the ideas behind CBT exercises and how those ideas can be applied in everyday situations.

                It is a self-help resource and does not replace individualized assessment or treatment from a qualified mental health professional.
            """.trimIndent()
        ),


        /*
         * ==================================================
         * 5. MINDFUL SELF-COMPASSION FOR BURNOUT
         * ==================================================
         */

        ReadingStory(
            id = "mindful_self_compassion_burnout",
            title = "Mindful Self-Compassion for Burnout",
            subtitle = "Kristin Neff & Christopher Germer • Stress and recovery",
            coverResId = R.drawable.mindful_self_compassion_burnout,
            story = """
                Mindful Self-Compassion for Burnout by Kristin Neff and Christopher Germer focuses on self-compassion in the context of prolonged stress and exhaustion.

                The book combines relatable experiences with practical self-compassion tools intended to help readers respond to themselves more supportively when they feel overwhelmed.

                It explores how people can recognize signs of exhaustion while developing a kinder and more supportive relationship with themselves.

                Why it may be useful:

                • Recognizing the effects of prolonged stress
                • Responding to yourself with compassion
                • Building healthier ways of coping
                • Making space for recovery
                • Working with feelings of exhaustion
                • Developing practical self-compassion habits

                Rather than encouraging people to simply push through difficult periods, the book explores how self-kindness and mindful awareness can become part of recovery.

                It may be particularly relevant for someone who feels mentally or emotionally drained.

                The book is a self-help resource and should not be treated as a diagnostic or medical resource.
            """.trimIndent()
        )
    )


    /*
     * ==========================================================
     * FIND BOOK
     * ==========================================================
     */

    fun getStoryById(
        id: String
    ): ReadingStory? {

        return stories.firstOrNull {
            it.id == id
        }
    }
}