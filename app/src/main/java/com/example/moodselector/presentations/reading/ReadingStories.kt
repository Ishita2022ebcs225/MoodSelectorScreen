package com.example.moodselector.presentations.reading

data class ReadingStory(
    val id: String,
    val title: String,
    val subtitle: String,
    val story: String
)

object ReadingStories {

    val stories = listOf(

        ReadingStory(
            id = "demotivated_day",
            title = "The Day I Couldn't Start",
            subtitle = "When motivation seems to disappear",
            story = """
                Maya woke up with a long list of things she wanted to get done.

                She needed to answer a few messages, finish some work, tidy her room, and make time for herself. None of it seemed particularly difficult. Still, when she looked at the list, she felt tired before she had even begun.

                "I'll start in a little while," she told herself.

                A little while became an hour.

                Then another.

                By the afternoon, Maya was frustrated with herself. She hadn't done much, yet somehow she felt exhausted.

                She started thinking about everything she should have accomplished.

                Maybe I’m becoming lazy.
                Maybe I’ve lost my motivation.
                Why can’t I just do what everyone else seems to do?

                She closed her phone and sat quietly for a moment.

                Then she noticed something.

                She didn't actually need to finish everything.

                She only needed to begin one small thing.

                Maya picked up the cup sitting beside her and took it to the kitchen. It took less than a minute.

                It wasn't a dramatic achievement. Nothing suddenly felt easy.

                But it reminded her that being stuck didn't mean she was incapable of moving.

                She chose one more small task.

                And then another.

                By the evening, her entire list wasn't finished.

                But she no longer felt like the day had been wasted.

                Sometimes motivation doesn't arrive before we begin.

                Sometimes, we find a little of it by taking the first small step.
            """.trimIndent()
        ),

        ReadingStory(
            id = "restless_evening",
            title = "A Restless Evening",
            subtitle = "When everything feels slightly unsettled",
            story = """
                It was a quiet evening, but Lina couldn't seem to settle.

                She had finished everything she needed to do. There were no urgent messages waiting for her. Nothing was particularly wrong.

                And yet, she kept moving from one thing to another.

                She opened her phone.

                Put it down.

                Made some tea.

                Checked the time.

                Opened her phone again.

                "Why do I feel like I need to be doing something?"

                She couldn't answer.

                Her thoughts weren't exactly frightening. They were simply everywhere.

                She remembered something she had said earlier that day. Then she thought about tomorrow. Then she remembered an unfinished task from last week.

                One thought led to another until her mind felt like a room with too many conversations happening at once.

                Lina finally put her phone away and sat near the window.

                She didn't try to solve every thought.

                She simply noticed them.

                There was the thought about tomorrow.

                There was the memory from earlier.

                There was the unfinished task.

                One by one, they seemed a little less urgent.

                Nothing outside her had changed.

                But she had stopped asking herself to figure everything out at once.

                The evening was still quiet.

                And slowly, she became quiet with it.
            """.trimIndent()
        ),

        ReadingStory(
            id = "too_much_on_mind",
            title = "Too Much on My Mind",
            subtitle = "When small worries begin to pile up",
            story = """
                Noah had been thinking about the same things all morning.

                There was a message she needed to reply to.

                A task she hadn't finished.

                Something she wanted to say to a friend.

                A decision she wasn't sure about.

                None of these things were enormous on their own.

                Together, they felt enormous.

                While making breakfast, she thought about work.

                While working, she thought about the message.

                While answering the message, she wondered whether she had written the right thing.

                By lunchtime, she felt like she had spent the entire morning solving problems without actually solving anything.

                She finally stopped.

                She took a piece of paper and wrote down everything that was occupying her mind.

                The list looked surprisingly ordinary.

                Some things could be done today.

                Some could wait.

                One thing wasn't even a problem yet. It was simply something she was imagining might become a problem.

                Seeing everything outside her head made a difference.

                Her life hadn't suddenly become easier.

                But the thoughts no longer had to compete for space in her mind.

                She chose one thing to deal with.

                The rest could wait.

                For the first time that day, Noah gave herself permission not to think about everything at once.

                Sometimes the mind becomes crowded not because everything is urgent, but because everything is being carried at the same time.
            """.trimIndent()
        ),

        ReadingStory(
            id = "second_guessing",
            title = "Maybe I Was Wrong",
            subtitle = "The exhausting habit of second-guessing yourself",
            story = """
                Sara had sent a simple message to a friend.

                A few minutes later, she read it again.

                Was that too much?

                She reread it.

                Maybe the wording sounded strange.

                She read it one more time.

                Perhaps she should have said something different.

                Her friend hadn't even responded yet.

                Still, Sara was already imagining every possible interpretation.

                She knew it was a small thing.

                But her mind wouldn't let it remain small.

                Eventually, she put her phone face down.

                She reminded herself that she had written the message with good intentions. She couldn't control exactly how someone else would read it.

                For a moment, she wanted to pick up the phone again.

                Instead, she left it where it was.

                Ten minutes passed.

                Then twenty.

                When she eventually checked her phone, her friend had replied normally.

                Sara smiled.

                The situation had never been as complicated as her thoughts had made it.

                She knew she might second-guess herself again another day.

                But now she had learned something useful:

                A worried thought can feel convincing without being true.

                And sometimes the kindest thing you can do is let a question remain unanswered for a little while.
            """.trimIndent()
        ),

        ReadingStory(
            id = "unproductive_day",
            title = "The Day That Didn't Go to Plan",
            subtitle = "Learning not to measure yourself by one difficult day",
            story = """
                Emma had planned the day perfectly.

                She would wake up early, exercise, finish her work, clean her room, and spend the evening doing something she enjoyed.

                Instead, she woke up later than planned.

                She missed her workout.

                She struggled to concentrate.

                By the afternoon, she felt disappointed.

                "I've wasted the whole day."

                The thought stayed with her.

                Because the day hadn't gone according to plan, Emma started treating it as though nothing she did afterward would matter.

                So she stopped trying.

                That evening, she looked around her room.

                The day hadn't been perfect.

                But she had answered an important message.

                She had completed part of her work.

                She had eaten lunch.

                She had taken a shower.

                She had even laughed at something her friend sent her.

                It wasn't the day she had planned.

                But it wasn't a complete failure either.

                Emma realized she had been judging the entire day by what she hadn't done.

                Tomorrow could be different.

                And perhaps today didn't need to be fixed.

                It simply needed to end.

                She turned off her light and decided that tomorrow she would begin again.

                Not because she had failed today.

                But because one imperfect day didn't deserve to decide what tomorrow would look like.
            """.trimIndent()
        ),

        ReadingStory(
            id = "comparing",
            title = "Everyone Else Seems Ahead",
            subtitle = "When comparison makes your own progress feel invisible",
            story = """
                On the way home, Aisha opened social media.

                Someone had started a new job.

                Someone else had moved into a new apartment.

                Another person was travelling.

                Someone she knew had achieved something she had been hoping to achieve herself.

                Aisha closed the app.

                For a while, she felt behind.

                It seemed like everyone was moving forward while she was standing still.

                Then she thought about something she had forgotten.

                She knew what other people chose to show.

                She didn't know what their ordinary days looked like.

                She didn't know how many times they had doubted themselves.

                She didn't know what they were struggling with privately.

                Most importantly, she realized she had been comparing someone else's visible moments with her own entire life.

                Aisha thought about the past year.

                She had handled things she once thought would be impossible.

                She had learned to say no sometimes.

                She had become more honest about what she needed.

                None of those things looked impressive on a screen.

                But they mattered.

                She put her phone away and continued walking.

                She wasn't behind.

                She was simply living a different life, at a different pace.

                And for tonight, that was enough.
            """.trimIndent()
        ),

        ReadingStory(
            id = "waiting_for_something",
            title = "What If Something Goes Wrong?",
            subtitle = "When ordinary moments become filled with worry",
            story = """
                Claire was sitting in a café waiting for a friend.

                Her friend was five minutes late.

                Then ten.

                Claire checked her phone.

                No new message.

                Her mind immediately began filling in the silence.

                Maybe something happened.

                Maybe she was upset with me.

                Maybe I did something wrong.

                Claire knew there were countless ordinary reasons someone could be late.

                Traffic.

                A forgotten alarm.

                A slow morning.

                Still, the worrying thought felt more convincing than all the ordinary explanations.

                She took a breath and looked around the café.

                Someone was reading a book.

                A couple were talking quietly.

                A child was trying to reach a spoon on a table.

                Nothing around her suggested that something was wrong.

                Claire reminded herself:

                "I don't have enough information to know what happened."

                That sentence didn't make the worry disappear.

                But it gave her somewhere else to stand.

                She didn't need to solve a problem that might not exist.

                A few minutes later, her friend arrived.

                "Sorry! The traffic was terrible."

                Claire smiled.

                The worry had felt real.

                The problem hadn't been.

                Sometimes peace doesn't come from knowing that everything will go perfectly.

                Sometimes it comes from accepting that we don't have to predict everything before it happens.
            """.trimIndent()
        ),

        ReadingStory(
            id = "starting_again",
            title = "Tomorrow Is Still There",
            subtitle = "Finding your way back after losing momentum",
            story = """
                For several days, Mia had been telling herself that she would get back into her routine.

                Tomorrow.

                She would exercise tomorrow.

                Tomorrow she would organize her work.

                Tomorrow she would sleep earlier.

                But tomorrow kept arriving and looking exactly like yesterday.

                One morning, Mia felt tired of making promises to herself.

                So she stopped making a big one.

                She didn't promise to completely change her routine.

                She simply put on her shoes and went outside for ten minutes.

                That was all.

                The next day, she did it again.

                Some days she walked for ten minutes.

                Some days she walked for twenty.

                One day she didn't go outside at all.

                Previously, she might have considered that a failure.

                This time, she simply continued the next day.

                Slowly, she understood something.

                Progress didn't require never slipping.

                It required returning.

                Again and again.

                There would always be another morning.

                Another opportunity to begin.

                And another chance to take one small step.

                Mia didn't need to become a completely different person.

                She only needed to keep finding her way back to herself.
            """.trimIndent()
        ),

        ReadingStory(
            id = "pause",
            title = "A Moment to Pause",
            subtitle = "When doing more isn't always the answer",
            story = """
                Daniel had spent the entire day trying to be productive.

                He answered messages while eating.

                He worked through his break.

                He made a list of things to do after work.

                When he finally sat down, he immediately wondered what he should do next.

                His body was tired.

                His mind wasn't.

                He felt guilty for resting.

                "I should be doing something."

                Then he noticed how strange that thought sounded.

                He had spent the entire day doing something.

                What he needed wasn't another task.

                He needed a pause.

                So Daniel put his phone away and sat near the window.

                For a few minutes, he did absolutely nothing.

                No planning.

                No checking.

                No catching up.

                Just a quiet moment.

                His problems hadn't disappeared.

                His responsibilities were still waiting.

                But his mind felt a little less crowded.

                He realized that rest wasn't something he had to earn by finishing everything.

                Sometimes a pause is part of taking care of yourself.

                Not every quiet moment needs to be productive.

                Some moments can simply be quiet.
            """.trimIndent()
        )
    )

    fun getStoryById(id: String): ReadingStory? {
        return stories.firstOrNull { it.id == id }
    }
}