import React, {Component} from 'react'
import {toast} from "react-toastify";

const BASE_URL = 'http://localhost:8120/api/';
const IMAGE_URL = 'good/download-file/';

class Good extends Component {

    constructor(props) {
        super(props);
        this.state = {image: {}}
    }

    componentDidMount() {
        fetch(BASE_URL + IMAGE_URL + this.props.el.images[0])
            .then(response => response.blob())
            .then(blob => this.setState({image: URL.createObjectURL(blob)}))
            .catch((err) => {
                toast.error(err.message, {position: toast.POSITION.TOP_RIGHT})
            });
    }

    render() {
        return (
            <div className="ui centered card">
                <div className="image">
                    <img src={this.state.image}/>
                </div>
                <div className="content">
                    <div className="header">{this.props.el.name}</div>
                </div>
                <div className="extra content">
                    <span className="right floated">
                        <i className="percent icon"/>
                        {this.props.el.discount}
                    </span>
                    <span className="left floated">
                        <i className="ruble sign icon"/>
                        <a>{this.props.el.price}</a>
                    </span>
                </div>
            </div>
        )
    }

}

export default Good