package org.grupo8

import domain.Content
import org.uqbar.commons.model.annotations.Observable

@Observable
class ContentVM(var content: Content) {

    var name = content.title

}
