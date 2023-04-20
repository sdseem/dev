
const App = {
    data() {
        return {
            
        }
    },
    methods: {

        addComment() {
            var div = document.createElement("div")
            div.className = "blog_comment"
            cur_id = "com_id_" + this.$com_count.toString()
            div.id = cur_id
            //
            var user_div = document.createElement("div")
            user_div.appendChild(document.createTextNode("Гость"))
            user_div.className = "user"
            div.appendChild(user_div)
            //
            var text_div = document.createElement("div")
            text_div.appendChild(document.createTextNode(document.getElementById('com_in').value))
            text_div.className = "comment_text"
            div.appendChild(text_div)
            //
            var delete_div = document.createElement("div")
            var delete_div_child = document.createElement("button")
            delete_div_child.appendChild(document.createTextNode("X"))
            delete_div_child.className = "comment_del_inner"
            const tmp_id = cur_id
            delete_div_child.onclick = function() {
                const deleted_element = document.getElementById(tmp_id)
                deleted_element.remove()
            }
            delete_div.appendChild(delete_div_child)
            delete_div.className = "comment_del"
            div.appendChild(delete_div)
            //
            var element = document.getElementById("bl_cmts")
            element.insertBefore(div, element.firstChild)
            this.$com_count++
        }
    },
    mounted() {
        axios.get('http://localhost:8088/api/v1/comments')
        .then(response => {
            var res_data = response.data
            for (index = 0; index < res_data.comments.length; ++index) {
                com_name = res_data.comments[index].user
                com_text = res_data.comments[index].comment
                //
                var div = document.createElement("div")
                div.className = "blog_comment"
                //
                var user_div = document.createElement("div")
                user_div.appendChild(document.createTextNode(com_name))
                user_div.className = "user"
                div.appendChild(user_div)
                //
                var text_div = document.createElement("div")
                text_div.appendChild(document.createTextNode(com_text))
                text_div.className = "comment_text"
                div.appendChild(text_div)
                //
                // var delete_div = document.createElement("div")
                // var delete_div_child = document.createElement("button")
                // delete_div_child.appendChild(document.createTextNode("X"))
                // delete_div_child.className = "comment_del_inner"
                // delete_div.appendChild(delete_div_child)
                // delete_div.className = "comment_del"
                // div.appendChild(delete_div)
                //
                var element = document.getElementById("bl_cmts")
                element.insertBefore(div, element.firstChild)
            }
        })
        .catch(error => {
            console.log(error)
        })
    }
}


const app = Vue.createApp(App)

app.config.globalProperties.$com_count = 0

app.mount('#blog_com')