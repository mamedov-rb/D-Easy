import React, {Component} from 'react'
import jsonStream from 'can-ndjson-stream';
import {toast} from "react-toastify";
import Good from './Good';

class Goods extends Component {

    constructor(props) {
        super(props);
        this.state = {goods: []}
    }

    componentDidMount() {
        this.fetchGoods()
    }

    fetchGoods = () => {
        let url = 'http://localhost:8040/api';
        let config = {
            headers: {
                'Content-Type': 'application/stream+json',
                'Accept': 'application/stream+json'
            }
        };
        fetch(url + '/menu-position/all', config)
            .then((response) => {
                return jsonStream(response.body);
            })
            .then((stream) => {
                const reader = stream.getReader();
                let read;
                reader.read().then(read = (result) => {
                    if (result.done) {
                        return;
                    }
                    this.setState({goods: [...this.state.goods, result.value]})
                    reader.read().then(read);
                });
            })
            .catch(err => {
                toast.error(err.response.data, {
                    position: toast.POSITION.TOP_RIGHT
                })
            });
    }

    // addProject = (project) => {
    //     api.post('/manager/project/save', project)
    //         .then(res => {
    //             toast.success("Project created.", {
    //                 position: toast.POSITION.TOP_RIGHT
    //             })
    //             this.fetchProjects()
    //         })
    //         .catch(err => {
    //             toast.error(err.response.data, {
    //                 position: toast.POSITION.TOP_RIGHT
    //             })
    //         })
    // }

    render() {
        return (
            <div>
                <div className="ui five doubling link cards">
                    {this.state.goods.map((el) => {
                        return (
                            <Good el={el}/>
                        )
                    })}
                </div>
            </div>
        )
    }

}

export default Goods