package org.grupo8

import domain.Category
import org.uqbar.commons.model.annotations.Observable

@Observable
class CategoryVM(val category: Category) {

    var name = category.name

}