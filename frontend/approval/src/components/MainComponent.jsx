import React, {Component} from "react";
import '../App.css';

class MainComponent extends Component {
    render() {
        return (
            <div className="Main">
                <div><a href="/writer/1">요청목록 및 결제요청 (John)</a></div>
                <div><a href="/approver/2">승인목록 및 승인 (James)</a></div>
            </div>
        )
    }
}

export default MainComponent