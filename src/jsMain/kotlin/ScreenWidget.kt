import androidx.compose.runtime.Composable
import data.ProjectRepository
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.attributes.alt
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*
import routes.skills.skills
import utils.*

@Composable
fun HomeCard(repository: ProjectRepository) {
    H3 {
        Img(skills.first { it.skillsSet.name == repository.language }.skillsSet.iconUrl) {
            alt(repository.language!!)
        }
        A("/projects#${repository.name}", repository.name)
    }

    Div {
        P(repository.description ?: "No description provided.")

        P {
            Text("URL: ")

            A(repository.htmlUrl, repository.repoName+"(${repository.appDownloads})")
        }
    }
}

//@Composable
//fun ProjectCard(repository: data.ProjectRepository, onClick: AttrsScope<HTMLDivElement>.(SyntheticMouseEvent) -> Unit = {}) {
//    var open by mutableStateOf(false)
//
//    Div({
//        classes(if (open) DataStyle.projectCardOpen else DataStyle.projectCardClosed, DataStyle.projectCard)
//        id(repository.name)
//
//        onClick {
//            open = !open
//            onClick(it)
//        }
//    }) {
//        Div({
//            classes("top", AppStyle.monoFont)
//        }) {
//            if (open) {
//                Img(src = repository.owner.avatarUrl)
//                H2 { Text(repository.name) }
//
//                Div({
//                    classes("texts")
//                }) {
//                    P({
//                        val creationDate = Date(repository.createdAt)
//                        val day = creationDate.getDate().toString().padStart(2, '0')
//                        val month = (creationDate.getMonth() + 1).toString().padStart(2, '0')
//                        val year = creationDate.getFullYear()
//                        val formatted = "$day/$month/$year"
//
//                        markdownParagraph(
//                            """
//							Creation date: <span>$formatted</span>
//							Stars: <span>${repository.stargazersCount}</span>
//							Commits: <span>${repository.commitsCount}</span>
//							Contributors: <span>${repository.contributorsCount}</span>
//						""".trimIndent(), true
//                        )
//                    })
//                }
//            } else {
//                Img(src = repository.owner.avatarUrl)
//                Div({
//                    classes("stars")
//                }) {
//                    TextIcon(repository.stargazersCount.toString(), FontAwesomeType.SOLID, "star")
//                }
//            }
//        }
//
//        Div({
//            classes("bottom")
//        }) {
//            if (open) {
//                val readmeContent = repository.readmeContent ?: repository.description ?: repository.name
//
//                P({
//                    markdownParagraph(readmeContent)
//                })
//            } else {
//                H3 { Text(repository.name) }
//                P(repository.description ?: "No description provided.")
//            }
//        }
//
//        if (open) {
//            A(repository.htmlUrl, {
//                classes(AppStyle.button)
//                target(ATarget.Blank)
//            }) {
//                Text("View on GitHub")
//            }
//        }
//    }
//}

object DataStyle : StyleSheet() {
    const val homeCardBackground = "#181818"
    const val homeCardTitleBackground = "#0e0e0e"
    const val homeCardColor = "#cacaca"
    const val projectCardClosedBackground = "#2a2b36"
    const val projectCardOpenBackgroundStart = "#66085190"
    const val projectCardOpenBackgroundEnd = "#39379490"

    val gridColumnStartVar by variable<StylePropertyNumber>()
    val imageSize by variable<CSSSizeValue<*>>()

    @OptIn(ExperimentalComposeWebApi::class)
    val homeCard by style {
        borderRadius(.8.cssRem)
        overflow(Overflow.Hidden)
        width(clamp(17.5.cssRem, 25.vw, 25.cssRem))

        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        justifyContent(JustifyContent.SpaceBetween)

        "h3" {
            display(DisplayStyle.Flex)
            justifyContent(JustifyContent.Center)
            alignItems(AlignItems.Center)
            gap(.5.cssRem)

            backgroundColor(Color(homeCardTitleBackground))
            margin(0.px)
            padding(1.cssRem)

            "a" {
                color(Color.white)
            }

            "img" {
                size(1.3.cssRem)
            }
        }

        "p" {
            color(Color(homeCardColor))
        }

        child(self, selector("div")) style {
            backgroundColor(Color(homeCardBackground))
            textAlign(TextAlign.Start)

            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            justifyContent(JustifyContent.SpaceBetween)
            gap(3.cssRem)

            height(100.percent)
            padding(1.cssRem)
        }

        combine(self, className("active")) style {
            transform {
                scale(1.05)
            }
        }
    }

    val projectCard by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        gap(1.cssRem)
        padding(clamp(1.6.cssRem, 2.vw, 3.5.cssRem))
        borderRadius(.75.cssRem)

        cursor(Cursor.Pointer)

        transitions {
            ease(AnimationTimingFunction.EaseInOut)
            duration(.5.s)
            properties("max-height")
        }

        "img" {
            size(imageSize.value())
            borderRadius(imageSize.value())
        }
    }

    val projectCardOpen by style {
        flexDirection(FlexDirection.Column)
        gap(2.cssRem)
        imageSize(5.cssRem)
        gridColumn("${gridColumnStartVar.value()} span")

        backgroundImage(linearGradient(135.deg) {
            stop(Color(projectCardOpenBackgroundStart))
            stop(Color(projectCardOpenBackgroundEnd))
        })
        maxHeight(500.cssRem)

        className("top") style {
            textAlign(TextAlign.Center)

            "h2" {
                fontSize(2.5.cssRem)
                margin(1.cssRem, 0.px)
            }

            "p" {
                textAlign(TextAlign.Start)
                margin(0.px, 1.5.cssRem)
            }

            child(self, type("div")) style {
                margin(auto)
                width(fitContent)
            }

            inline fun subSpanColor(color: CSSColorValue, index: Int) {
                child(type("p"), type("span")) + nthOfType(index.n) style {
                    color(color)
                }
            }

            subSpanColor(Color("#B4BBFF"), 1)
            subSpanColor(Color("#FFE24B"), 2)
            subSpanColor(Color("#75C9CE"), 3)
            subSpanColor(Color("#64E881"), 4)
        }

        className("bottom") style {
            backgroundColor(Color("#ffffff20"))
            borderRadius(.75.cssRem)
            padding(clamp(2.cssRem, 2.vw, 3.5.cssRem))
            maxWidth(90.percent)

            "h1" {
                marginTop(0.px)
            }

            "code" {
                whiteSpace("pre-wrap")
            }
        }

        media(mediaMaxWidth(AppStyle.mobileFourthBreak)) {
            self {
                padding(clamp(.8.cssRem, 1.vw, 1.6.cssRem))
            }

            className("bottom") style {
                maxWidth(95.percent)
                padding(clamp(.8.cssRem, 1.vw, 2.cssRem))
            }
        }
    }

    val projectCardClosed by style {
        val maxSize by variable<CSSSizeValue<*>>()
        maxSize(6.cssRem)

        flexDirection(FlexDirection.Row)
        imageSize(3.5.cssRem)

        backgroundColor(Color(projectCardClosedBackground))
        height(maxSize.value())
        maxHeight(maxSize.value())

        className("top") style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            alignItems(AlignItems.Center)
            textAlign(TextAlign.Center)
            gap(.5.cssRem)

            className("stars") style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.RowReverse)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.Center)
                gap(.5.cssRem)
            }
        }

        className("bottom") style {
            group(type("h3"), type("p")) style {
                property("text-ellipsis", "ellipsis")
                margin(0.px)
            }

            "h3" {
                marginBottom(.5.cssRem)
            }
        }

        media(mediaMaxWidth(AppStyle.mobileFirstBreak)) {
            self {
                maxSize(8.cssRem)
                padding(1.cssRem)
            }
        }

        media(mediaMaxWidth(AppStyle.mobileFourthBreak)) {
            self {
                imageSize(3.cssRem)
                height(auto)
                maxHeight("none")

                "h3" {
                    fontSize(1.1.cssRem)
                }
            }
        }
    }
}